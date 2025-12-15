package com.example.projectCommunity.models.message;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.chat.Chat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing a message sent to a specific chat.
 *
 * <p>A message can be sent by a user that is participant to that specific project,
 * and it's received by the rest of the participants. A message lives inside it's chat's history.</p>
 * */
@Entity
@Table(
        indexes = {
                @Index(name = "idx_message_chat_id", columnList = "chat_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Message {

    /** Unique identifier of the message */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Content of the message */
    @Column(columnDefinition = "TEXT")
    private String content;

    /** Timestamp of creation of the message */
    private LocalDateTime timestamp;

    /** Chat that the message was sent into */
    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private Chat chat;

    /** Participant user that sent the message */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private User sender;
}
