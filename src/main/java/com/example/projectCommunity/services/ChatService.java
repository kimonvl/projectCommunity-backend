package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ChatService {
    ResponseEntity<ResponseDTO<MessageDTO>> sendMessage(SendMessageRequest sendMessageRequest);
    ResponseEntity<ResponseDTO<ChatDTO>> fetchActiveChat(long projectId, String email );
}
