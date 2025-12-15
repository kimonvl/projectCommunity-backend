package com.example.projectCommunity.models.notification;

import com.example.projectCommunity.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing a notification delivered to a user.
 *
 * <p>A notification is triggered by an event in the system and,
 * it's purpose is to inform the interested users about the event.</p>
 * */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {

    /** Unique identifier of the notification*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** User that performed the event that triggered the notification */
    @ManyToOne
    private User sender;

    /** Target user that wants to be informed about the event */
    @ManyToOne
    private User receiver;

    /** Type of the notification (describes the trigger event)*/
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    /** Message for the target user*/
    private String message;

    /** Flag whether the notification is seen or not*/
    private boolean seen = false;

    /** Timestamp of creation of the notification */
    private LocalDateTime createdAt = LocalDateTime.now();

    /** Optional JSON metadata associated with the notification, depending on the type */
    @Lob
    private String metadata;
}
