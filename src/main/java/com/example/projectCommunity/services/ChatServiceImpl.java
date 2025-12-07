package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.mappers.ChatMapper;
import com.example.projectCommunity.mappers.MessageMapper;
import com.example.projectCommunity.mappers.UserMapper;
import com.example.projectCommunity.models.chat.Chat;
import com.example.projectCommunity.models.message.Message;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.ChatRepo;
import com.example.projectCommunity.repos.MessageRepo;
import com.example.projectCommunity.repos.ProjectRepo;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public ResponseEntity<ResponseDTO<MessageDTO>> sendMessage(SendMessageRequest sendMessageRequest) {
        Chat chat = chatRepo.findById(sendMessageRequest.getChatId());
        Optional<User> senderOpt = userRepo.findById(sendMessageRequest.getSenderId());
        if (chat == null) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Chat not found", false), HttpStatus.BAD_REQUEST);
        }
        if (senderOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User not found", false), HttpStatus.BAD_REQUEST);
        }
        User user = senderOpt.get();
        Project project = projectRepo.findByChatId(sendMessageRequest.getChatId());
        Boolean hasAccess = projectRepo.userHasAccessToProject(project.getId(), user.getEmail());
        if (hasAccess == null || !hasAccess) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User does not have access to this project", false), HttpStatus.BAD_REQUEST);
        }
        Message message = new Message();
        message.setChat(chat);
        message.setContent(sendMessageRequest.getContent());
        message.setSender(user);
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepo.save(message);
        MessageDTO messageDTO = messageMapper.toDto(savedMessage);

        //Send the message to connected users throw websocket
        simpMessagingTemplate.convertAndSend(
                "/topic/chat/" + messageDTO.getChatId(),
                messageDTO
        );

        return new ResponseEntity<>(new ResponseDTO<>(messageDTO, "Message sent", true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDTO<ChatDTO>> fetchActiveChat(long projectId, String email) {
        User user = userRepo.findByEmail(email);
        Chat chat = chatRepo.findByProjectId(projectId);
        ChatDTO chatDTO;
        if (user == null){
            return new ResponseEntity<>(new ResponseDTO<>(null, "User doesn't exist", false), HttpStatus.BAD_REQUEST);
        }
        if (chat == null){
            return new ResponseEntity<>(new ResponseDTO<>(null, "Chat doesn't exist", false), HttpStatus.BAD_REQUEST);
        }
        if (!chat.getProject().getParticipants().contains(user)) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User doesn't have access to this chat", false), HttpStatus.BAD_REQUEST);
        }
        chatDTO = chatMapper.toDto(chat);
        chatDTO.setParticipants(chat.getProject().getParticipants().stream().map(userMapper:: toDto).collect(Collectors.toSet()));
        return new ResponseEntity<>(new ResponseDTO<>(chatDTO, "Chat fetched", true), HttpStatus.ACCEPTED);
    }
}
