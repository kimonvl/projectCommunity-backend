package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.mappers.NotificationMapper;
import com.example.projectCommunity.models.notification.IssueCreatedMetadata;
import com.example.projectCommunity.models.notification.Notification;
import com.example.projectCommunity.models.notification.NotificationType;
import com.example.projectCommunity.models.notification.ProjectInviteMetadata;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.NotificationRepo;
import com.example.projectCommunity.repos.ProjectRepo;
import com.example.projectCommunity.repos.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendProjectInviteNotification(List<User> receivers, User sender, Project project) {
        ObjectMapper mapper = new ObjectMapper();
        Notification notification = new Notification();

        for (User receiver : receivers) {
            if (receiver == null) {
                new ResponseEntity<>(new ResponseDTO<>(null, "Receiver not found", false), HttpStatus.BAD_REQUEST);
                return;
            }
            notification.setSender(sender);
            notification.setReceiver(receiver);
            notification.setType(NotificationType.PROJECT_INVITE);
            notification.setMessage(sender.getEmail() + " invites you to his project");
            ProjectInviteMetadata metadata = new ProjectInviteMetadata();
            metadata.setProjectId(project.getId());
            metadata.setProjectTitle(project.getTitle());
            metadata.setOwnerEmail(sender.getEmail());
            try {
                notification.setMetadata(mapper.writeValueAsString(metadata));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            notification = notificationRepo.save(notification);
            simpMessagingTemplate.convertAndSendToUser(
                    receiver.getEmail(),
                    "/queue/notifications",
                    notificationMapper.toDto(notification)
            );
        }
        new ResponseEntity<>(new ResponseDTO<>(null, "Invitation sent", true), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<NotificationDTO>>> getUnseenNotifications(String email) {
        List<Notification> notifications = notificationRepo.findByReceiverEmailAndSeenFalse(email);
        List<NotificationDTO> notificationDTOs = notificationMapper.toDtoList(notifications);
        return new ResponseEntity<>(new ResponseDTO<>(notificationDTOs, "Notifications fetched", true), HttpStatus.ACCEPTED);
    }

    @Override
    public long markAsSeen(long notificationId) throws Exception {
        Optional<Notification> notificationOpt = notificationRepo.findById(notificationId);
        Notification notification;
        if (notificationOpt.isEmpty())
            throw new Exception("Notification not found");
        notification = notificationOpt.get();
        notification.setSeen(true);
        return notificationRepo.save(notification).getId();
    }

    @Override
    public void sendIssueCreatedNotification(IssueCreatedMetadata issueCreatedMetadata, Set<User> receivers) {
        ObjectMapper mapper = new ObjectMapper();
        User sender = userRepo.findByEmail(issueCreatedMetadata.getCreatorEmail());
        for (User receiver : receivers) {
            System.out.println("Sending to: " + receiver.getEmail());

            if (receiver.equals(sender))
                continue;
            Notification notification = new Notification();
            notification.setMessage(sender.getEmail() + " created a new issue in " + issueCreatedMetadata.getProjectTitle());
            notification.setSender(sender);
            notification.setReceiver(receiver);
            notification.setType(NotificationType.ISSUE_CREATED);
            try {
                notification.setMetadata(mapper.writeValueAsString(issueCreatedMetadata));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            NotificationDTO notificationDto = notificationMapper.toDto(notificationRepo.save(notification));
            System.out.println("sending invitation");
            simpMessagingTemplate.convertAndSendToUser(
                    receiver.getEmail(),
                    "queue/notifications",
                    notificationDto
            );
        }
    }
}
