package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;

import java.util.List;

/**
 * Service interface defining issue related operations.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *     <li>Creating an issue for a specific project</li>
 *     <li>Retrieving a project's issues</li>
 *     <li>Assigning a project participant to an issue</li>
 *     <li>Retrieving the details of an issue</li>
 *     <li>Changing the status of an issue</li>
 *     <li>Deleting an issue</li>
 * </ul>
 * */
public interface IssueService {

    /**
     * Creates an issue for a specific project.
     *
     * @param createIssueRequest payload containing the issue details and the identifier of the target project
     * @param email email address of the creator
     * @return the created {@link IssueDTO}
     * */
    IssueDTO createIssue(CreateIssueRequest createIssueRequest, String email);

    /**
     * Retrieves the issues of a specific project.
     *
     * @param projectId the identifier of the target project
     * @param email email address of the user requesting the issues
     * @return a List of the project's {@link IssueDTO}
     * */
    List<IssueDTO> getProjectIssues(long projectId, String email);

    /**
     * Assigns a project participant into an issue.
     *
     * @param assignUserToIssueRequest payload containing the target issue identifier and the assigned user identifier
     * @param email email address of the user performing the assignment
     * @return the assigned {@link UserDTO}
     * */
    UserDTO assignUser(AssignUserToIssueRequest assignUserToIssueRequest, String email);

    /**
     * Retrieves the details of an issue based on its identifier.
     *
     * @param issueId the identifier of the target issue
     * @param email email address of the user requesting the issue details
     * @return the retrieved {@link IssueDTO}
     * */
    IssueDTO getIssueDetails(long issueId, String email);

    /**
     * Alters the status of an issue.
     *
     * @param changeIssueStatusRequest payload containing the target issue's identifier and the new status
     * @param email email address of the user performing the status change
     * @return the altered {@link IssueDTO}
     * */
    IssueDTO changeStatus(ChangeIssueStatusRequest changeIssueStatusRequest, String email);

    /**
     * Deletes an issue based on its identifier.
     *
     * @param issueId the identifier of the target issue
     * @param email email address of the user deleting the issue (owner)
     * @return the identifier of the deleted issue
     * */
    Long deleteIssue(long issueId, String email);
}
