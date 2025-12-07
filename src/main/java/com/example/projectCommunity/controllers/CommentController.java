package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.CreateCommentRequest;
import com.example.projectCommunity.DTOs.response.CommentDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    ResponseEntity<ResponseDTO<CommentDTO>> createComment(@RequestBody CreateCommentRequest createCommentRequest, Principal principal) {
        return commentService.createComment(createCommentRequest, principal.getName());
    }

    @GetMapping("/getIssueComments/{issueId}")
    ResponseEntity<ResponseDTO<List<CommentDTO>>> getIssueComments(@PathVariable long issueId, Principal principal) {
        return commentService.getIssueComments(issueId, principal.getName());
    }
}
