package com.example.projectCommunity.DTOs.response;

import com.example.projectCommunity.models.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private long id;
    private UserDTO sender;
    private NotificationType type;
    private String message;
    private boolean seen;
    private LocalDateTime createdAt;
    private String metadata;
}
