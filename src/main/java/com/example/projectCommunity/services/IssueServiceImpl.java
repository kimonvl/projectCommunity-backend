package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.mappers.IssueMapper;
import com.example.projectCommunity.mappers.UserMapper;
import com.example.projectCommunity.models.issue.Issue;
import com.example.projectCommunity.models.issue.IssueStatus;
import com.example.projectCommunity.models.notification.IssueCreatedMetadata;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.IssueRepo;
import com.example.projectCommunity.repos.ProjectRepo;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{
    @Autowired
    IssueRepo issueRepo;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    NotificationService notificationService;


    @Override
    public ResponseEntity<ResponseDTO<IssueDTO>> createIssue(CreateIssueRequest createIssueRequest, String email) {
        User user = userRepo.findByEmail(email);
        Project project;
        Issue issue = new Issue();
        IssueDTO issueDTO;
        Optional<Project> projectOpt = projectRepo.findById(createIssueRequest.getProjectId());
        if (projectOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Project not found", false), HttpStatus.BAD_REQUEST);
        }

        project =projectOpt.get();
        if (!project.getParticipants().contains(user)) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User does not have access to this project", false), HttpStatus.BAD_REQUEST);
        }

        issue.setTitle(createIssueRequest.getTitle());
        issue.setDescription(createIssueRequest.getDescription());
        issue.setCreator(user);
        issue.setProject(project);
        issue.setStatus(IssueStatus.PENDING);
        issue = issueRepo.save(issue);
        issueDTO = issueMapper.toDto(issue);

        notificationService.sendIssueCreatedNotification(new IssueCreatedMetadata(user.getEmail(), project.getId(), issue.getId(), project.getTitle()), project.getParticipants());

        return new ResponseEntity<>(new ResponseDTO<>(issueDTO, "Issue created", true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<IssueDTO>>> getProjectIssues(long projectId, String email) {
        Boolean hasAccess = projectRepo.userHasAccessToProject(projectId, email);
        if (hasAccess == null || !hasAccess) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User does not have access to this project", false), HttpStatus.BAD_REQUEST);
        }
        List<Issue> issues = issueRepo.findByProjectId(projectId);
        List<IssueDTO> issueDTOs = issueMapper.toDtoList(issues);
        return new ResponseEntity<>(new ResponseDTO<>(issueDTOs, "Issues fetched", true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseDTO<UserDTO>> assignUser(AssignUserToIssueRequest assignUserToIssueRequest, String email) {
        Optional<Issue> issueOpt = issueRepo.findById(assignUserToIssueRequest.getIssueId());
        Optional<User> assignedUserOpt = userRepo.findById(assignUserToIssueRequest.getUserId());
        User assigneeUser = userRepo.findByEmail(email);
        if (issueOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Issue doesn't exist", false), HttpStatus.BAD_REQUEST);
        }
        if (assignedUserOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User doesn't exist", false), HttpStatus.BAD_REQUEST);
        }
        User assignedUser = assignedUserOpt.get();
        Issue issue = issueOpt.get();
        if (!issue.getProject().getParticipants().contains(assignedUser)) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Assigned User is not participant in project", false), HttpStatus.BAD_REQUEST);
        }
        if (!issue.getProject().getParticipants().contains(assigneeUser)) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "You don't have authority on this project", false), HttpStatus.BAD_REQUEST);
        }
        issue.getAssignedUsers().add(assignedUser);
        issueRepo.save(issue);
        UserDTO assignedUserDTO = userMapper.toDto(assignedUser);
        return new ResponseEntity<>(new ResponseDTO<>(assignedUserDTO, "User assigned", true), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<ResponseDTO<IssueDTO>> getIssueDetails(long issueId, String email) {
        Optional<Issue> issueOpt = issueRepo.findById(issueId);
        if (issueOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Issue not found", false), HttpStatus.BAD_REQUEST);
        }
        Issue issue = issueOpt.get();
        Boolean hasAccess = projectRepo.userHasAccessToProject(issue.getProject().getId(), email);
        if (hasAccess == null || !hasAccess) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User does not have access to this Issue", false), HttpStatus.BAD_REQUEST);
        }
        IssueDTO issueDTO = issueMapper.toDto(issue);
        return new ResponseEntity<>(new ResponseDTO<>(issueDTO, "Issue details fetched", true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseDTO<IssueDTO>> changeStatus(ChangeIssueStatusRequest changeIssueStatusRequest, String email) {
        Optional<Issue> issueOpt = issueRepo.findById(changeIssueStatusRequest.getIssueId());
        if (issueOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Issue doesn't exist", false), HttpStatus.BAD_REQUEST);
        }
        Issue issue = issueOpt.get();
        Boolean hasAccess = projectRepo.userHasAccessToProject(issue.getProject().getId(), email);
        if (hasAccess == null || !hasAccess) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User does not have access to this Issue", false), HttpStatus.BAD_REQUEST);
        }
        issue.setStatus(changeIssueStatusRequest.getStatus());
        issue = issueRepo.save(issue);
        IssueDTO issueDTO = issueMapper.toDto(issue);
        return new ResponseEntity<>(new ResponseDTO<>(issueDTO, "Status changed", true), HttpStatus.CREATED);
    }
}
