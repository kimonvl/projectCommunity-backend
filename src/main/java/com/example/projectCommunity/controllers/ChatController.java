package com.example.projectCommunity.controllers;

import com.example.projectCommunity.models.chat.Chat;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Rest controller responsible for {@link Chat} related activities
 * in the context of a selected {@link Project}.
 *
 * <p>It exposes endpoints responsible for sending a message to ,
 * and fetching the message history of, a specific chat.
 * All activities are performed within the context of an authenticated user. It returns standardized API responses.</p>
 *
 * */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    /**
     * Sends a message to a specific chat.
     *
     * @param sendMessageRequest a {@link SendMessageRequest} container with the request's data: chat id, content.
     * @param principal the authenticated user's data, that is sending the message,  provided by Spring Security.
     * @return http response containing the created {@link MessageDTO}.
     * */
    @PostMapping("/sendMessage")
    ResponseEntity<ResponseDTO<MessageDTO>> sendMessage(@RequestBody SendMessageRequest sendMessageRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(chatService.sendMessage(sendMessageRequest, principal.getName()), MessageConstants.MESSAGE_SENT, HttpStatus.CREATED);
    }

    /**
     * Retrieves the chat and, it's message history, of a specific project.
     *
     * @param projectId the identifier of the project whose chat is requested.
     * @param principal the authenticated user's data, that requested the chat,  provided by Spring Security.
     * @return http response containing the requested {@link ChatDTO}.
     * */
    @GetMapping("/activeChat/{projectId}")
    ResponseEntity<ResponseDTO<ChatDTO>> fetchActiveChat(@PathVariable long projectId, Principal principal) {
        return ResponseFactory.createSuccessResponse(chatService.fetchActiveChat(projectId, principal.getName()), MessageConstants.CHAT_FETCHED, HttpStatus.ACCEPTED);
    }
}
