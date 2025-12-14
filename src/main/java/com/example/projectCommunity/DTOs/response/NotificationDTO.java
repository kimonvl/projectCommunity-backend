package com.example.projectCommunity.DTOs.response;

import com.example.projectCommunity.models.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object representing a notification associated with an event.
 *
 * <p>This DTO is returned by notification-related endpoints and contains the sender,
 * the type, the message for the receiver, and the notification metadata depending on type.</p>
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    /** Unique identifier of the notification*/
    private long id;
    /** Sender of the notification*/
    private UserDTO sender;
    /** Type of the notification */
    private NotificationType type;
    /** Message for the receiver*/
    private String message;
    /** Flag of wether user have seen the notification*/
    private boolean seen;
    /** Timestamp of notification creation*/
    private LocalDateTime createdAt;
    /** Object converted to string holding information about the notification based on notification type*/
    private String metadata;
}
