package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByIssueId(long issueId);
}
