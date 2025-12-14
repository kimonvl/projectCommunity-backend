package com.example.projectCommunity.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * Data transfer object representing a chat associated with a project.
 *
 * <p>This DTO is returned by chat-related endpoints and contains the participants,
 * and the message history of a chat.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    /** Unique identifier of the chat */
    private long id;
    /** Participants of the chat */
    private Set<UserDTO> participants;
    /** Message history of the chat */
    private List<MessageDTO> messages;
}
