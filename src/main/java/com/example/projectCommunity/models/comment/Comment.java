package com.example.projectCommunity.models.comment;

import com.example.projectCommunity.models.issue.Issue;
import com.example.projectCommunity.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;
    private LocalDateTime createdAt = LocalDateTime.now();

}
