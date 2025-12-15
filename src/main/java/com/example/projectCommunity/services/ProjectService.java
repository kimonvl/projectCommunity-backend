package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;

import java.util.List;

/**
 * Service interface defining project related operations.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *     <li>Creating a new project by a user</li>
 *     <li>Retrieving the projects in which a user participates</li>
 *     <li>Retrieving the details of a specific project</li>
 *     <li>Accepting a project invitation</li>
 *     <li>Sending a project invitation</li>
 * </ul>
 * */
public interface ProjectService {

    /**
     * Creates a new project based on the details provided by a user.
     *
     * @param createProjectRequest payload containing the project details provided by the user-creator
     * @param email email address of the creator-user
     * @return the created {@link ProjectDTO}
     * */
    ProjectDTO createProject(CreateProjectRequest createProjectRequest, String email);

    /**
     * Retrieves the projects in which a user participates.
     *
     * @param email email address of the user requesting the projects
     * @return the List of the retrieved {@link ProjectDTO}s
     * */
    List<ProjectDTO> getMyProjects(String email);

    /**
     * Retrieves the details of a selected project.
     *
     * @param projectId the identifier of the target project
     * @param email email address of the user requesting the project details
     * @return the retrieved {@link ProjectDTO}
     * */
    ProjectDTO getSelectedProject(long projectId, String email);

    /**
     * Accepts a project invitation.
     *
     * @param email email address of the user accepting the invitation
     * @param projectId the identifier of the target project
     * @param notificationId the identifier of the notification associated with the project invitation
     * @return the {@link ProjectDTO} representing the project that the user just joined
     * */
    ProjectDTO acceptProjectInvitation(String email, long projectId, long notificationId);

    /**
     * Sends a project invitation to a list of users.
     *
     * @param receiverEmails email addresses of users to be invited
     * @param projectId the identifier of the target project
     * @param email email address of the user sending the invitation
     * */
    void sendProjectInvitation(List<String> receiverEmails, long projectId, String email);
}
