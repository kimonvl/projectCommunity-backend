package com.example.projectCommunity.models.message;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.chat.Chat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private User sender;
}
