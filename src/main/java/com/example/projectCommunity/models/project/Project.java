package com.example.projectCommunity.models.project;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.chat.Chat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;
    @ElementCollection
    private List<String> tags;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;
    @ManyToMany
    @JoinTable(
            name = "project_participants",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> participants = new HashSet<>();
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Chat chat;
}
