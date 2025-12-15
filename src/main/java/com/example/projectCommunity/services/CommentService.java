package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateCommentRequest;
import com.example.projectCommunity.DTOs.response.CommentDTO;

import java.util.List;

/**
 * Service interface defining comment related operations.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *     <li>Creating a comment for a specific issue</li>
 *     <li>Retrieving an issue's comments</li>
 * </ul>
 * */
public interface CommentService {

    /**
     * Creates a comment for an issue.
     *
     * @param createCommentRequest payload containing the comment content and the target issue's identifier
     * @param email email address of the author
     * @return the created {@link CommentDTO}
     * */
    CommentDTO createComment(CreateCommentRequest createCommentRequest, String email);

    /**
     * Retrieves the comment history of a specific issue.
     *
     * @param issueId identifier of the target issue
     * @param email email address of the user requesting the comments
     * @return a List of {@link CommentDTO}
     * */
    List<CommentDTO> getIssueComments(long issueId, String email);
}
