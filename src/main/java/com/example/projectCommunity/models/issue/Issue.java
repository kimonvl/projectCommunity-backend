package com.example.projectCommunity.models.issue;

import com.example.projectCommunity.models.comment.Comment;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an issue associated with a project.
 *
 * <p>An issue is created by the owner or the participants of the project.
 * An issue can be assigned to multiple users that they belong to the participants of the project,
 * or they are the owner.</p>
 * */
@Entity
@Table(
        indexes = {
                @Index(name = "idx_issue_project_id", columnList = "project_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Issue {

    /** Unique identifier of the issue */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    /** Title of the issue */
    private String title;

    /** Description of the issue */
    private String description;

    /** Status of progress of the issue */
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    /** Comment history of the issue */
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /** User that created the issue */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    /** Project that the issue belongs to */
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    /** Users assigned to the issue */
    @ManyToMany
    @JoinTable(
            name = "issue_assigned_users",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedUsers = new ArrayList<>();

    /** Timestamp of creation of the issue */
    private LocalDateTime createdAt = LocalDateTime.now();
}
