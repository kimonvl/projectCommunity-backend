package com.example.projectCommunity.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private long id;
    private String content;
    private LocalDateTime timestamp;
    private UserDTO sender;
    private long chatId;
}
