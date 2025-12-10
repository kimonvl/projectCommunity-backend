package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.exceptions.ProjectNotFoundException;
import com.example.projectCommunity.exceptions.UserNotFoundException;
import com.example.projectCommunity.mappers.ProjectMapper;
import com.example.projectCommunity.models.chat.Chat;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
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
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public ProjectDTO createProject(CreateProjectRequest createProjectRequest, String email) {
        User user = userRepo.findByEmail(email);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }

        Project project = new Project();
        project.setTitle(createProjectRequest.getTitle());
        project.setDescription(createProjectRequest.getDescription());
        project.setCategory(createProjectRequest.getCategory());
        project.setTags(createProjectRequest.getTags());
        project.setOwner(user);
        project.getParticipants().add(user);

        Chat chat = new Chat();
        chat.setProject(project);
        project.setChat(chat);
        Project savedProject = projectRepo.save(project);
        //convert to dto with mapstruct
        return projectMapper.toDto(savedProject);
    }

    @Override
    public List<ProjectDTO> getMyProjects(String email) {
        List<Project> projects = projectRepo.findByParticipantsEmail(email);
        return projectMapper.toDtoList(projects);
    }

    @Override
    public ProjectDTO getSelectedProject(long projectId, String email) {
        User user = userRepo.findByEmail(email);
        Project project;
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException("Project not found");
        }
        project = projectOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo,project.getId(), user.getEmail(), "User doesn't have access to project");
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDTO acceptProjectInvitation(String email, long projectId, long notificationId) {
        User user = userRepo.findByEmail(email);
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        Project project;
        ProjectDTO projectDTO;
        if(projectOpt.isEmpty()) {
            throw new ProjectNotFoundException("Project not found");
        }
        notificationService.markAsSeen(notificationId);

        project = projectOpt.get();
        project.getParticipants().add(user);
        return projectMapper.toDto(projectRepo.save(project));
    }

    @Override
    public void sendProjectInvitation(List<String> receiverEmails, long projectId, String email) {
        User sender = userRepo.findByEmail(email);
        List<User> receivers = userRepo.findByEmailIn(receiverEmails);
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        Project project;
        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException("Project not found");
        }
        project = projectOpt.get();
        notificationService.sendProjectInviteNotification(receivers, sender, project);
    }
}
