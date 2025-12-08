package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;

public interface ChatService {
    MessageDTO sendMessage(SendMessageRequest sendMessageRequest, String email);
    ChatDTO fetchActiveChat(long projectId, String email );
}
