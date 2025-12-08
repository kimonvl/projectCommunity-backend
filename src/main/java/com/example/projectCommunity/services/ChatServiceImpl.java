package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.exceptions.ChatNotFoundException;
import com.example.projectCommunity.exceptions.UserNotFoundException;
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
import com.example.projectCommunity.services.serviceUtils.ServiceUtils;
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
    public MessageDTO sendMessage(SendMessageRequest sendMessageRequest, String email) {
        Chat chat = chatRepo.findById(sendMessageRequest.getChatId());
        User user = userRepo.findByEmail(email);
        if (chat == null) {
            throw new ChatNotFoundException("Chat not found");
        }
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        ServiceUtils.checkAccessToProject(projectRepo, chat.getProject().getId(), user.getEmail());

        Message message = new Message();
        message.setChat(chat);
        message.setContent(sendMessageRequest.getContent());
        message.setSender(user);
        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepo.save(message);
//        chat.getMessages().add(savedMessage);
//        chatRepo.save(chat);
        MessageDTO messageDTO = messageMapper.toDto(savedMessage);

        //Send the message to connected users throw websocket
        simpMessagingTemplate.convertAndSend(
                "/topic/chat/" + messageDTO.getChatId(),
                messageDTO
        );

        return messageDTO;
    }

    @Override
    public ChatDTO fetchActiveChat(long projectId, String email) {
        User user = userRepo.findByEmail(email);
        Chat chat = chatRepo.findByProjectId(projectId);
        ChatDTO chatDTO;
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        if (chat == null){
            throw new ChatNotFoundException("Chat not found");
        }
        ServiceUtils.checkAccessToProject(projectRepo, chat.getProject().getId(), user.getEmail());

        chatDTO = chatMapper.toDto(chat);
        chatDTO.setParticipants(chat.getProject().getParticipants().stream().map(userMapper:: toDto).collect(Collectors.toSet()));
        return chatDTO;
    }
}
