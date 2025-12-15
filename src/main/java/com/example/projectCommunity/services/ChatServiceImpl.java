package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.exceptions.ChatNotFoundException;
import com.example.projectCommunity.exceptions.UserNotFoundException;
import com.example.projectCommunity.mappers.ChatMapper;
import com.example.projectCommunity.mappers.MessageMapper;
import com.example.projectCommunity.mappers.UserMapper;
import com.example.projectCommunity.models.chat.Chat;
import com.example.projectCommunity.models.message.Message;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.ChatRepo;
import com.example.projectCommunity.repos.MessageRepo;
import com.example.projectCommunity.repos.ProjectRepo;
import com.example.projectCommunity.repos.UserRepo;
import com.example.projectCommunity.services.serviceUtils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ChatService}.
 * */
@Service
public class ChatServiceImpl implements ChatService {

    //Repos
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private MessageRepo messageRepo;

    //Mappers
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private UserMapper userMapper;

    //Messaging
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * {@inheritDoc}
     * */
    @Override
    public MessageDTO sendMessage(SendMessageRequest sendMessageRequest, String email) {
        Chat chat = chatRepo.findById(sendMessageRequest.getChatId());
        User user = userRepo.findByEmail(email);
        if (chat == null) {
            throw new ChatNotFoundException(MessageConstants.CHAT_NOT_FOUND);
        }
        if (user == null) {
            throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND);
        }
        ServiceUtils.checkAccessToProject(projectRepo, chat.getProject().getId(), user.getEmail(), MessageConstants.USER_NOT_IN_PROJECT);

        Message message = new Message();
        message.setChat(chat);
        message.setContent(sendMessageRequest.getContent());
        message.setSender(user);
        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepo.save(message);
        MessageDTO messageDTO = messageMapper.toDto(savedMessage);

        //Send the message to connected users via Websocket
        simpMessagingTemplate.convertAndSend(
                "/topic/chat/" + messageDTO.getChatId(),
                messageDTO
        );

        return messageDTO;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public ChatDTO fetchActiveChat(long projectId, String email) {
        User user = userRepo.findByEmail(email);
        Chat chat = chatRepo.findByProjectId(projectId);
        ChatDTO chatDTO;
        if (user == null){
            throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND);
        }
        if (chat == null){
            throw new ChatNotFoundException(MessageConstants.CHAT_NOT_FOUND);
        }
        ServiceUtils.checkAccessToProject(projectRepo, chat.getProject().getId(), user.getEmail(), MessageConstants.USER_NOT_IN_PROJECT);

        chatDTO = chatMapper.toDto(chat);
        chatDTO.setParticipants(chat.getProject().getParticipants().stream().map(userMapper:: toDto).collect(Collectors.toSet()));
        return chatDTO;
    }
}
