package com.example.projectCommunity.models.comment;

import com.example.projectCommunity.models.issue.Issue;
import com.example.projectCommunity.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing a comment associated with an issue.
 *
 * <p>A comment to an issue can be created by a user that is both a participant of the project containing the issue and,
 * assigned to that issue.</p>
 * */
@Entity
@Table(
        indexes = {
                @Index(name = "idx_comment_issue_id", columnList = "issue_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {

    /** Unique identifier of the comment */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    /** Content of the comment */
    private String content;

    /** User that created the comment */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /** Issue to which belongs the comment */
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    /** Timestamp of creation of the comment */
    private LocalDateTime createdAt = LocalDateTime.now();

}
