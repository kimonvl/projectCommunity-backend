package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.exceptions.IssueNotFoundException;
import com.example.projectCommunity.exceptions.ProjectNotFoundException;
import com.example.projectCommunity.exceptions.UserNoAccessException;
import com.example.projectCommunity.exceptions.UserNotFoundException;
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
import com.example.projectCommunity.services.serviceUtils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public IssueDTO createIssue(CreateIssueRequest createIssueRequest, String email) {
        User user = userRepo.findByEmail(email);
        Project project;
        Issue issue = new Issue();
        IssueDTO issueDTO;
        Optional<Project> projectOpt = projectRepo.findById(createIssueRequest.getProjectId());
        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException(MessageConstants.PROJECT_NOT_FOUND);
        }

        project =projectOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo, project.getId(), user.getEmail(), MessageConstants.USER_NOT_IN_PROJECT);

        issue.setTitle(createIssueRequest.getTitle());
        issue.setDescription(createIssueRequest.getDescription());
        issue.setCreator(user);
        issue.setProject(project);
        issue.setStatus(IssueStatus.PENDING);
        issue = issueRepo.save(issue);
        issueDTO = issueMapper.toDto(issue);

        notificationService.sendIssueCreatedNotifications(new IssueCreatedMetadata(user.getEmail(), project.getId(), issue.getId(), project.getTitle()), project.getParticipants());

        return issueDTO;
    }

    @Override
    public List<IssueDTO> getProjectIssues(long projectId, String email) {
        ServiceUtils.checkAccessToProject(projectRepo, projectId, email, MessageConstants.USER_NOT_IN_PROJECT);
        return issueMapper.toDtoList(issueRepo.findByProjectId(projectId));
    }

    @Override
    public UserDTO assignUser(AssignUserToIssueRequest assignUserToIssueRequest, String email) {
        Optional<Issue> issueOpt = issueRepo.findById(assignUserToIssueRequest.getIssueId());
        Optional<User> assignedUserOpt = userRepo.findById(assignUserToIssueRequest.getUserId());
        User assigneeUser = userRepo.findByEmail(email);
        if (issueOpt.isEmpty()) {
            throw new IssueNotFoundException(MessageConstants.ISSUE_NOT_FOUND);
        }
        if (assignedUserOpt.isEmpty()) {
            throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND);
        }
        User assignedUser = assignedUserOpt.get();
        Issue issue = issueOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo, issue.getProject().getId(), assigneeUser.getEmail(), MessageConstants.USER_NOT_IN_PROJECT);
        ServiceUtils.checkAccessToProject(projectRepo, issue.getProject().getId(), assignedUser.getEmail(), MessageConstants.INVITED_USER_NOT_IN_PROJECT);

        issue.getAssignedUsers().add(assignedUser);

        issueRepo.save(issue);
        return userMapper.toDto(assignedUser);
    }

    @Override
    public IssueDTO getIssueDetails(long issueId, String email) {
        Optional<Issue> issueOpt = issueRepo.findById(issueId);
        if (issueOpt.isEmpty()) {
            throw new IssueNotFoundException(MessageConstants.ISSUE_NOT_FOUND);
        }
        Issue issue = issueOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo, issue.getProject().getId(), email, MessageConstants.USER_NOT_IN_PROJECT);
        return issueMapper.toDto(issue);
    }

    @Override
    public IssueDTO changeStatus(ChangeIssueStatusRequest changeIssueStatusRequest, String email) {
        Optional<Issue> issueOpt = issueRepo.findById(changeIssueStatusRequest.getIssueId());
        if (issueOpt.isEmpty()) {
            throw new IssueNotFoundException(MessageConstants.ISSUE_NOT_FOUND);
        }
        Issue issue = issueOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo, issue.getProject().getId(), email, MessageConstants.USER_NOT_IN_PROJECT);
        issue.setStatus(changeIssueStatusRequest.getStatus());
        return issueMapper.toDto(issueRepo.save(issue));
    }

    public Long deleteIssue(long issueId, String email) {
        System.out.println(issueId);
        User user = userRepo.findByEmail(email);
        Optional<Issue> issueOpt = issueRepo.findById(issueId);
        if (issueOpt.isEmpty()) {
            throw new IssueNotFoundException(MessageConstants.ISSUE_NOT_FOUND);
        }
        Issue issue = issueOpt.get();
        if (issue.getCreator().getId() != user.getId()) {
            throw new UserNoAccessException(MessageConstants.USER_NO_ACCESS_TO_ISSUE);
        }
        Long deletedId = issue.getId();
        issueRepo.delete(issue);
        return deletedId;
    }
}
