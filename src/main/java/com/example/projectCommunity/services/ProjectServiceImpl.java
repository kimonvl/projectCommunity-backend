package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.CreateProjectRequest;
import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.constants.MessageConstants;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ProjectService}.
 * */
@Service
public class ProjectServiceImpl implements ProjectService{

    // Repos
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectRepo projectRepo;

    // Services
    @Autowired
    private NotificationService notificationService;

    // Mappers
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * {@inheritDoc}
     * */
    @Override
    public ProjectDTO createProject(CreateProjectRequest createProjectRequest, String email) {
        User user = userRepo.findByEmail(email);
        if (user == null){
            throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND);
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

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<ProjectDTO> getMyProjects(String email) {
        List<Project> projects = projectRepo.findByParticipantsEmail(email);
        return projectMapper.toDtoList(projects);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public ProjectDTO getSelectedProject(long projectId, String email) {
        User user = userRepo.findByEmail(email);
        Project project;
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException(MessageConstants.PROJECT_NOT_FOUND);
        }
        project = projectOpt.get();
        ServiceUtils.checkAccessToProject(projectRepo,project.getId(), user.getEmail(), MessageConstants.USER_NOT_IN_PROJECT);
        return projectMapper.toDto(project);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Adds the user in the project participants and marks as seen the associated notification.</p>
     * */
    @Override
    public ProjectDTO acceptProjectInvitation(String email, long projectId, long notificationId) {
        User user = userRepo.findByEmail(email);
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        Project project;
        if(projectOpt.isEmpty()) {
            throw new ProjectNotFoundException(MessageConstants.PROJECT_NOT_FOUND);
        }
        notificationService.markAsSeen(notificationId);

        project = projectOpt.get();
        project.getParticipants().add(user);
        return projectMapper.toDto(projectRepo.save(project));
    }

    /**
     * {@inheritDoc}
     *
     * <p>Retrieves the target project and pass it in the notification service to attach it with the notification.</p>
     * */
    @Override
    public void sendProjectInvitation(List<String> receiverEmails, long projectId, String email) {
        User sender = userRepo.findByEmail(email);
        List<User> receivers = userRepo.findByEmailIn(receiverEmails);
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        Project project;
        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException(MessageConstants.PROJECT_NOT_FOUND);
        }
        project = projectOpt.get();
        notificationService.sendProjectInviteNotification(receivers, sender, project);
    }
}
