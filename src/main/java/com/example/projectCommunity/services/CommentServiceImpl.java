package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateCommentRequest;
import com.example.projectCommunity.DTOs.response.CommentDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.mappers.CommentMapper;
import com.example.projectCommunity.models.comment.Comment;
import com.example.projectCommunity.models.issue.Issue;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.CommentRepo;
import com.example.projectCommunity.repos.IssueRepo;
import com.example.projectCommunity.repos.ProjectRepo;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    IssueRepo issueRepo;
    @Autowired
    ProjectRepo projectRepo;

    @Override
    public ResponseEntity<ResponseDTO<CommentDTO>> createComment(CreateCommentRequest createCommentRequest, String email) {
        User user = userRepo.findByEmail(email);
        Optional<Issue> issueOpt = issueRepo.findById(createCommentRequest.getIssueId());
        if (issueOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Issue not found", false), HttpStatus.BAD_REQUEST);
        }
        Issue issue = issueOpt.get();
        Boolean hasAccess = projectRepo.userHasAccessToProject(issue.getProject().getId(), email);
        if (hasAccess == null || !hasAccess) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "No access to issue", false), HttpStatus.BAD_REQUEST);
        }
        Comment comment = new Comment();
        comment.setContent(createCommentRequest.getContent());
        comment.setAuthor(user);
        comment.setIssue(issue);
        comment = commentRepo.save(comment);
        CommentDTO commentDTO = commentMapper.toDto(comment);
        return new ResponseEntity<>(new ResponseDTO<>(commentDTO, "Comment created", true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> getIssueComments(long issueId, String email) {
        List<Comment> comments = commentRepo.findByIssueId(issueId);
        Optional<Issue> issueOpt = issueRepo.findById(issueId);
        if (issueOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Issue not found", false), HttpStatus.BAD_REQUEST);
        }
        Issue issue = issueOpt.get();
        Boolean hasAccess = projectRepo.userHasAccessToProject(issue.getProject().getId(), email);
        if (hasAccess == null || !hasAccess) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "No access to issue", false), HttpStatus.BAD_REQUEST);
        }
        List<CommentDTO> commentDTOs = commentMapper.toDtoList(comments);
        return new ResponseEntity<>(new ResponseDTO<>(commentDTOs, "Comments fetched", true), HttpStatus.ACCEPTED);
    }
}
