package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateCommentRequest;
import com.example.projectCommunity.DTOs.response.CommentDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.exceptions.IssueNotFoundException;
import com.example.projectCommunity.mappers.CommentMapper;
import com.example.projectCommunity.models.comment.Comment;
import com.example.projectCommunity.models.issue.Issue;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.CommentRepo;
import com.example.projectCommunity.repos.IssueRepo;
import com.example.projectCommunity.repos.ProjectRepo;
import com.example.projectCommunity.repos.UserRepo;
import com.example.projectCommunity.services.serviceUtils.ServiceUtils;
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
    public CommentDTO createComment(CreateCommentRequest createCommentRequest, String email) {
        User user = userRepo.findByEmail(email);
        Optional<Issue> issueOpt = issueRepo.findById(createCommentRequest.getIssueId());
        if (issueOpt.isEmpty()) {
            throw new IssueNotFoundException("Issue not found");
        }
        Issue issue = issueOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo, issue.getProject().getId(), user.getEmail(), "User doesn't have access to this project");
        Comment comment = new Comment();
        comment.setContent(createCommentRequest.getContent());
        comment.setAuthor(user);
        comment.setIssue(issue);
        return commentMapper.toDto(commentRepo.save(comment));
    }

    @Override
    public List<CommentDTO> getIssueComments(long issueId, String email) {
        User user = userRepo.findByEmail(email);
        List<Comment> comments = commentRepo.findByIssueId(issueId);
        Optional<Issue> issueOpt = issueRepo.findById(issueId);
        if (issueOpt.isEmpty()) {
            throw new IssueNotFoundException("Issue not found");
        }
        Issue issue = issueOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo, issue.getProject().getId(), user.getEmail(), "User doesn't have access to this project");
        return commentMapper.toDtoList(comments);
    }
}
