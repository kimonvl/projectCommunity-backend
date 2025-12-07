package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/activeChat/{projectId}")
    ResponseEntity<ResponseDTO<ChatDTO>> fetchActiveChat(@PathVariable long projectId, Principal principal) {
        return chatService.fetchActiveChat(projectId, principal.getName());
    }

    @PostMapping("/sendMessage")
    ResponseEntity<ResponseDTO<MessageDTO>> sendMessage(@RequestBody SendMessageRequest sendMessageRequest) {
        return chatService.sendMessage(sendMessageRequest);
    }
}
