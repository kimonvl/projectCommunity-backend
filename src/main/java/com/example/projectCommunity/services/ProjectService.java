package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    ResponseEntity<ResponseDTO<ProjectDTO>> createProject(CreateProjectRequest createProjectRequest, String email);
    ResponseEntity<ResponseDTO<List<ProjectDTO>>> getMyProjects(String email);
    ResponseEntity<ResponseDTO<ProjectDTO>> getSelectedProject(long projectId, String email);
    ResponseEntity<ResponseDTO<ProjectDTO>> acceptProjectInvitation(String email, long projectId, long notificationId);
    ResponseEntity<ResponseDTO<Object>> sendProjectInvitation(List<String> receiverEmails, long projectId, String email);
}
