package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(CreateProjectRequest createProjectRequest, String email);
    List<ProjectDTO> getMyProjects(String email);
    ProjectDTO getSelectedProject(long projectId, String email);
    ProjectDTO acceptProjectInvitation(String email, long projectId, long notificationId);
    void sendProjectInvitation(List<String> receiverEmails, long projectId, String email);
}
