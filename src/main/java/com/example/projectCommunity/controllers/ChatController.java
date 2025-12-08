package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/sendMessage")
    ResponseEntity<ResponseDTO<MessageDTO>> sendMessage(@RequestBody SendMessageRequest sendMessageRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(chatService.sendMessage(sendMessageRequest, principal.getName()), "Message sent", HttpStatus.CREATED);
    }

    @GetMapping("/activeChat/{projectId}")
    ResponseEntity<ResponseDTO<ChatDTO>> fetchActiveChat(@PathVariable long projectId, Principal principal) {
        return ResponseFactory.createSuccessResponse(chatService.fetchActiveChat(projectId, principal.getName()), "Chat history fetched", HttpStatus.ACCEPTED);
    }
}
