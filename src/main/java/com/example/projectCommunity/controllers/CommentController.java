package com.example.projectCommunity.controllers;

import com.example.projectCommunity.models.comment.Comment;
import com.example.projectCommunity.models.issue.Issue;
import com.example.projectCommunity.DTOs.requests.CreateCommentRequest;
import com.example.projectCommunity.DTOs.response.CommentDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Rest controller responsible for {@link Comment} related activities
 * in the context of an {@link Issue}.
 *
 * <p>It exposes endpoints responsible for creating a comment for an issue and,
 * retrieving the comments of the selected issue.
 * All activities are performed within the context of an authenticated user. It returns standardized API responses.</p>
 * */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    /**
     * Creates a {@link Comment} for a specific {@link Issue}.
     *
     * @param createCommentRequest a {@link CreateCommentRequest} with the request's data: issue id, content.
     * @param principal the authenticated user's data, that is sending the message,  provided by Spring Security.
     * @return http response containing the created {@link CommentDTO}.
     * */
    @PostMapping("/create")
    ResponseEntity<ResponseDTO<CommentDTO>> createComment(@RequestBody CreateCommentRequest createCommentRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(commentService.createComment(createCommentRequest, principal.getName()), MessageConstants.COMMENT_CREATED, HttpStatus.CREATED);
    }

    /**
     * Retrieves the comment history of a specific {@link Issue}.
     *
     * @param issueId identifier of the issue whose comments are requested.
     * @param principal the authenticated user's data, that requested the comment history,  provided by Spring Security.
     * @return http response containing the requested list of {@link CommentDTO}.
     * */
    @GetMapping("/getIssueComments/{issueId}")
    ResponseEntity<ResponseDTO<List<CommentDTO>>> getIssueComments(@PathVariable long issueId, Principal principal) {
        return ResponseFactory.createSuccessResponse(commentService.getIssueComments(issueId, principal.getName()), MessageConstants.COMMENTS_FETCHED, HttpStatus.ACCEPTED);
    }
}
