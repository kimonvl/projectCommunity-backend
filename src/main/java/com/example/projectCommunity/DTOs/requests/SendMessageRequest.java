package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload used to send a message in a specific chat.
 *
 * <p>This DTO carries the content of the message and,
 * the identifier of the target chat.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {
    /** Identifier of the target chat */
    private long chatId;
    /** Content of the message */
    private String content;
}
