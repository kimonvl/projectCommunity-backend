package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.chat.Chat;
import com.example.projectCommunity.models.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Comment} entities.
 *
 * <p>Provides data access operations for comment persistence including:</p>
 * <ul>
 *     <li>Retrieving based on the associated issue's identifier</li>
 * </ul>
 *
 * */
public interface CommentRepo extends JpaRepository<Comment, Long> {

    /**
     * Retrieves a List of comments associated with a specific issue.
     *
     * @param issueId the associated issue's identifier
     * @return the List of comment entities, or empty list if not found any.
     * */
    List<Comment> findByIssueId(long issueId);
}
