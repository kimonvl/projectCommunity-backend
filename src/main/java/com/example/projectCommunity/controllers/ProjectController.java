package com.example.projectCommunity.controllers;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.DTOs.requests.AcceptProjectInvitationRequest;
import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.requests.ProjectInvitationRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Rest controller responsible for {@link Project} related activities
 * in the context of an authenticated {@link User}.
 *
 * <p>It exposes endpoints responsible for creating a project,
 * retrieving the projects that user participates in,
 * retrieving the details of a specific project,
 * sending a join to project invitation,
 * accepting a project invitation.
 * All activities are performed within the context of an authenticated user. It returns standardized API responses.</p>
 * */
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    /**
     * Creates a {@link Project} for a specific {@link User}.
     *
     * @param createProjectRequest a {@link CreateProjectRequest} with the request's data: title, description, category, tags.
     * @param principal the authenticated user's data, that is creating the project,  provided by Spring Security.
     * @return http response containing the created {@link ProjectDTO}.
     * */
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ProjectDTO>> createProject(@RequestBody CreateProjectRequest createProjectRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.createProject(createProjectRequest, principal.getName()), MessageConstants.PROJECT_CREATED, HttpStatus.CREATED);
    }

    /**
     * Retrieves the {@link Project}s that a specific {@link User} is participating.
     *
     * @param principal the authenticated user's data, that is requesting the projects,  provided by Spring Security.
     * @return http response containing the retrieved {@link List<ProjectDTO>}.
     * */
    @GetMapping("/myProjects")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getMyProjects(Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.getMyProjects(principal.getName()), MessageConstants.PROJECTS_FETCHED, HttpStatus.ACCEPTED);
    }

    /**
     * Retrieves the details of a specific {@link Project}.
     *
     * @param projectId identifier of the requested project.
     * @param principal the authenticated user's data, that is requesting the project,  provided by Spring Security.
     * @return http response containing the retrieved {@link ProjectDTO}.
     * */
    @GetMapping("/selectedProject/{projectId}")
    public ResponseEntity<ResponseDTO<ProjectDTO>> getSelectedProject(@PathVariable long projectId, Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.getSelectedProject(projectId, principal.getName()), MessageConstants.PROJECT_FETCHED, HttpStatus.ACCEPTED);
    }

    /**
     * Sends a {@link Project} invitation to a list of users.
     *
     * @param projectInvitationRequest a {@link ProjectInvitationRequest} with the request's data: receiver emails, project id.
     * @param principal the authenticated user's data, that is inviting the other users,  provided by Spring Security.
     * @return http response of success.
     * */
    @PostMapping("/sendProjectInvitation")
    ResponseEntity<ResponseDTO<Object>> sendProjectInvitation(@RequestBody ProjectInvitationRequest projectInvitationRequest, Principal principal) {
        projectService.sendProjectInvitation(projectInvitationRequest.getReceiverEmails(), projectInvitationRequest.getProjectId(), principal.getName());
        return ResponseFactory.createSuccessResponse(null, MessageConstants.PROJECT_INVITATION_SENT, HttpStatus.CREATED);
    }

    /**
     * Accepts a {@link Project} invitation.
     *
     * @param acceptProjectInvitationRequest a {@link AcceptProjectInvitationRequest} with the request's data: project id, notification id.
     * @param principal the authenticated user's data, that is accepting the invitation,  provided by Spring Security.
     * @return http response of the {@link ProjectDTO} that just joined.
     * */
    @PostMapping("/acceptInvitation")
    public ResponseEntity<ResponseDTO<ProjectDTO>> acceptProjectInvitation(@RequestBody AcceptProjectInvitationRequest acceptProjectInvitationRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.acceptProjectInvitation(principal.getName(), acceptProjectInvitationRequest.getProjectId(), acceptProjectInvitationRequest.getNotificationId()), MessageConstants.PROJECT_INVITATION_ACCEPT, HttpStatus.CREATED);
    }
}
