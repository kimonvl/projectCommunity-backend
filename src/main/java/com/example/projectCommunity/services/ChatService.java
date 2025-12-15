package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.SendMessageRequest;
import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.DTOs.response.MessageDTO;

/**
 * Service interface defining chat related operations.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *     <li>Sending a message into a project's chat</li>
 *     <li>Retrieving the chat's message history</li>
 * </ul>
 * */
public interface ChatService {

    /**
     * Creates a message entity from the given request and publishes it to the chat participants via Websocket.
     *
     * @param sendMessageRequest payload containing message content and chat identifier
     * @param email the email address of the sender
     * */
    MessageDTO sendMessage(SendMessageRequest sendMessageRequest, String email);

    /**
     * Retrieves the message history of a project's chat based on the project's identifier.
     *
     * @param projectId the identifier of the target project
     * @param email email address of the user
     * @return the requested project's {@link ChatDTO}
     * */
    ChatDTO fetchActiveChat(long projectId, String email );
}
