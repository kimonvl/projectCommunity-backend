package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.mappers.ProjectMapper;
import com.example.projectCommunity.models.chat.Chat;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.ProjectRepo;
import com.example.projectCommunity.repos.UserRepo;
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
    public ResponseEntity<ResponseDTO<ProjectDTO>> createProject(CreateProjectRequest createProjectRequest, String email) {
        User user = userRepo.findByEmail(email);
        ProjectDTO projectDTO = new ProjectDTO();
        if (user == null){
            return new ResponseEntity<>(new ResponseDTO<>(null, "User doesn't exist", false), HttpStatus.BAD_REQUEST);
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
        projectDTO = projectMapper.toDto(savedProject);
        return new ResponseEntity<>(new ResponseDTO<>(projectDTO, "Project created", true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getMyProjects(String email) {
        List<Project> projects = projectRepo.findByParticipantsEmail(email);
        List<ProjectDTO> projectDTOS = projectMapper.toDtoList(projects);
        return new ResponseEntity<>(new ResponseDTO<>(projectDTOS, "Projects fetched", true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseDTO<ProjectDTO>> getSelectedProject(long projectId, String email) {
        User user = userRepo.findByEmail(email);
        Project project;
        ProjectDTO projectDTO;
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Project not found", false), HttpStatus.BAD_REQUEST);
        }
        project = projectOpt.get();
        if (!project.getParticipants().contains(user)) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "User doesn't have access to this project", false), HttpStatus.BAD_REQUEST);
        }
        projectDTO = projectMapper.toDto(project);
        return new ResponseEntity<>(new ResponseDTO<>(projectDTO, "Project found", true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseDTO<ProjectDTO>> acceptProjectInvitation(String email, long projectId, long notificationId) {
        User user = userRepo.findByEmail(email);
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        Project project;
        ProjectDTO projectDTO;
        if(projectOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Project not found", false), HttpStatus.BAD_REQUEST);
        }
        try {
            notificationService.markAsSeen(notificationId);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
        project = projectOpt.get();
        project.getParticipants().add(user);
        projectDTO = projectMapper.toDto(projectRepo.save(project));

        return new ResponseEntity<>(new ResponseDTO<>(projectDTO, "Joined to project: " + project.getTitle(), true), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseDTO<Object>> sendProjectInvitation(List<String> receiverEmails, long projectId, String email) {
        User sender = userRepo.findByEmail(email);
        List<User> receivers = userRepo.findByEmailIn(receiverEmails);
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        Project project;
        if (projectOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Project not found", false), HttpStatus.BAD_REQUEST);
        }
        project = projectOpt.get();
        System.out.println("send project invitation project service");
        notificationService.sendProjectInviteNotification(receivers, sender, project);
        return new ResponseEntity<>(new ResponseDTO<>(null, "Invitation sent", true), HttpStatus.CREATED);
    }
}
