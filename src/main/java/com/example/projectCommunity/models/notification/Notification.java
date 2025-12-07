package com.example.projectCommunity.models.notification;

import com.example.projectCommunity.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String message;
    private boolean seen = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    @Lob
    private String metadata;
}
