package com.example.projectCommunity.models.chat;

import com.example.projectCommunity.models.message.Message;
import com.example.projectCommunity.models.project.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a chat associated with a project.
 *
 * <p>When a new project is created a chat is created and bound to that project automatically.
 * The chat holds the messages exchanged between the participants of the project.
 * Chat participants are equal to the associated project's participants.</p>
 * */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Chat {

    /** Unique identifier of the chat */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Project that the chat is associated with */
    @OneToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    /** Message history of the chat */
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
}
