package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.AcceptProjectInvitationRequest;
import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.requests.ProjectInvitationRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ProjectDTO>> createProject(@RequestBody CreateProjectRequest createProjectRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.createProject(createProjectRequest, principal.getName()), "Project created", HttpStatus.CREATED);
    }

    @GetMapping("/myProjects")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getMyProjects(Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.getMyProjects(principal.getName()), "Projects fetched", HttpStatus.ACCEPTED);
    }

    @GetMapping("/selectedProject/{projectId}")
    public ResponseEntity<ResponseDTO<ProjectDTO>> getSelectedProject(@PathVariable long projectId, Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.getSelectedProject(projectId, principal.getName()), "Project fetched", HttpStatus.ACCEPTED);
    }

    @PostMapping("/sendProjectInvitation")
    ResponseEntity<ResponseDTO<Object>> sendProjectInvitation(@RequestBody ProjectInvitationRequest projectInvitationRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(null, "Project invitation sent", HttpStatus.CREATED);
    }

    @PostMapping("/acceptInvitation")
    public ResponseEntity<ResponseDTO<ProjectDTO>> acceptProjectInvitation(@RequestBody AcceptProjectInvitationRequest acceptProjectInvitationRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(projectService.acceptProjectInvitation(principal.getName(), acceptProjectInvitationRequest.getProjectId(), acceptProjectInvitationRequest.getNotificationId()), "Project invitation accepted", HttpStatus.CREATED);
    }
}
