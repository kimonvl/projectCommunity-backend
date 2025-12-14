package com.example.projectCommunity.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object representing a message associated with a chat.
 *
 * <p>This DTO is returned by chat-related endpoints and contains the sender,
 * the message content, the identifier of the target chat and the timestamp of sending.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    /** Unique identifier of the message */
    private long id;
    /** Content of the message */
    private String content;
    /** Sender of the message*/
    private UserDTO sender;
    /** Identifier of the target chat */
    private long chatId;
    /** Timestamp of message sending */
    private LocalDateTime timestamp;
}
