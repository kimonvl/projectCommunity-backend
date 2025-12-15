package com.example.projectCommunity.models.project;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.chat.Chat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a project created by a user.
 *
 * <p>When a project is created a chat is created and bound into it.
 * The creator of the project can add other users as participants.
 * Only it's participants have access to a project.</p>
 * */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {

    /** Unique identifier of the project */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Title of the project */
    private String title;

    /** Description of the project */
    private String description;

    /** Category of the project */
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    /** Tags associated with the project */
    @ElementCollection
    private List<String> tags;

    /** User that created the project */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    /** Users participating in the project */
    @ManyToMany
    @JoinTable(
            name = "project_participants",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> participants = new HashSet<>();

    /** Chat associated with the project */
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Chat chat;
}
