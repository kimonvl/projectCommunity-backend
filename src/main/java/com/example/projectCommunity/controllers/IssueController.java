package com.example.projectCommunity.controllers;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.issue.Issue;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Rest controller responsible for {@link Issue} related activities
 * in the context of a {@link Project}.
 *
 * <p>It exposes endpoints responsible for creating an issue,
 * retrieving a project's issues,
 * assigning a user in to the issue,
 * retrieving the details of specific issue,
 * changing the status of an issue,
 * deleting an issue.
 * All activities are performed within the context of an authenticated user. It returns standardized API responses.</p>
 * */
@Controller
@RequestMapping("/issue")
public class IssueController {
    @Autowired
    IssueService issueService;

    /**
     * Creates an {@link Issue} for a specific {@link Project}.
     *
     * @param createIssueRequest a {@link CreateIssueRequest} with the request's data: title, description, project id.
     * @param principal the authenticated user's data, that is sending the message,  provided by Spring Security.
     * @return http response containing the created {@link IssueDTO}.
     * */
    @PostMapping("/create")
    ResponseEntity<ResponseDTO<IssueDTO>> createIssue(@RequestBody CreateIssueRequest createIssueRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.createIssue(createIssueRequest, principal.getName()), MessageConstants.ISSUE_CREATED, HttpStatus.CREATED);
    }

    /**
     * Retrieves the {@link Issue}s of a specific {@link Project}.
     *
     * @param projectId identifier of the project whose issues are requested.
     * @param principal the authenticated user's data, that requests the issues,  provided by Spring Security.
     * @return http response containing the retrieved list of {@link IssueDTO}.
     * */
    @GetMapping("/getProjectIssues/{projectId}")
    ResponseEntity<ResponseDTO<List<IssueDTO>>> getProjectIssues(@PathVariable long projectId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.getProjectIssues(projectId, principal.getName()), MessageConstants.ISSUES_FETCHED, HttpStatus.ACCEPTED);
    }

    /**
     * Assigns a {@link User} to a specific {@link Issue}.
     *
     * @param assignUserToIssueRequest a {@link AssignUserToIssueRequest} with the request's data: issue id, user id.
     * @param principal the authenticated user's data, that is performing the assignation,  provided by Spring Security.
     * @return http response containing the assigned {@link User}.
     * */
    @PostMapping("/assignUser")
    ResponseEntity<ResponseDTO<UserDTO>> assignUser(@RequestBody AssignUserToIssueRequest assignUserToIssueRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.assignUser(assignUserToIssueRequest, principal.getName()), MessageConstants.USER_ASSIGNED_TO_ISSUE, HttpStatus.CREATED);
    }

    /**
     * Retrieves the details of a specific {@link Issue}.
     *
     * @param issueId identifier of the requested issue.
     * @param principal the authenticated user's data, that requests the issue,  provided by Spring Security.
     * @return http response containing the retrieved {@link IssueDTO}.
     * */
    @GetMapping("/getIssueDetails/{issueId}")
    ResponseEntity<ResponseDTO<IssueDTO>> getIssueDetails(@PathVariable long issueId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.getIssueDetails(issueId, principal.getName()), MessageConstants.ISSUE_FETCHED, HttpStatus.ACCEPTED);
    }

    /**
     * Changes the status of a specific {@link Issue}.
     *
     * @param changeIssueStatusRequest a {@link ChangeIssueStatusRequest} with the request's data: issue id, status.
     * @param principal the authenticated user's data, that changed the status,  provided by Spring Security.
     * @return http response containing the updated {@link IssueDTO}.
     * */
    @PostMapping("/changeStatus")
    ResponseEntity<ResponseDTO<IssueDTO>> changeStatus(@RequestBody ChangeIssueStatusRequest changeIssueStatusRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.changeStatus(changeIssueStatusRequest, principal.getName()), MessageConstants.ISSUE_STATUS_CHANGED, HttpStatus.CREATED);
    }

    /**
     * Deletes a specific {@link Issue}.
     *
     * @param issueId identifier of the issue to be deleted.
     * @param principal the authenticated user's data, that deletes the issue,  provided by Spring Security.
     * @return http response containing the identifier of the deleted issue.
     * */
    @PostMapping("/delete")
    ResponseEntity<ResponseDTO<Long>> deleteIssue(@RequestBody long issueId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.deleteIssue(issueId, principal.getName()), MessageConstants.ISSUE_DELETED, HttpStatus.CREATED);
    }
}
