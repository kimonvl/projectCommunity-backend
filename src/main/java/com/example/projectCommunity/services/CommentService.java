package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateCommentRequest;
import com.example.projectCommunity.DTOs.response.CommentDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CreateCommentRequest createCommentRequest, String email);

    List<CommentDTO> getIssueComments(long issueId, String email);
}
