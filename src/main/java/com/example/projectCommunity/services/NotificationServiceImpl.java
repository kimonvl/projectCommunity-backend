package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.exceptions.NotificationNotFoundException;
import com.example.projectCommunity.exceptions.UserNotFoundException;
import com.example.projectCommunity.mappers.NotificationMapper;
import com.example.projectCommunity.models.notification.IssueCreatedMetadata;
import com.example.projectCommunity.models.notification.Notification;
import com.example.projectCommunity.models.notification.NotificationType;
import com.example.projectCommunity.models.notification.ProjectInviteMetadata;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.NotificationRepo;
import com.example.projectCommunity.repos.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link NotificationService}.
 * */
@Service
public class NotificationServiceImpl implements NotificationService{

    //Repos
    @Autowired
    UserRepo userRepo;
    @Autowired
    NotificationRepo notificationRepo;

    //Mappers
    @Autowired
    NotificationMapper notificationMapper;

    //Messaging
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * {@inheritDoc}
     *
     * <p> Creates and serializes a {@link ProjectInviteMetadata} into a JSON string
     * and saves it in notification metadata field. Then sends the notification via Websocket</p>
     * */
    @Override
    public void sendProjectInviteNotification(List<User> receivers, User sender, Project project) {
        for (User receiver : receivers) {
            if (receiver == null) {
                throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND);
            }
            Notification notification = getNotification(sender, project, receiver);

            notification = notificationRepo.save(notification);
            simpMessagingTemplate.convertAndSendToUser(
                    receiver.getEmail(),
                    "/queue/notifications",
                    notificationMapper.toDto(notification)
            );
        }
    }

    private static Notification getNotification(User sender, Project project, User receiver) {
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setType(NotificationType.PROJECT_INVITE);
        notification.setMessage(sender.getEmail() + " invites you to his project");
        ProjectInviteMetadata metadata = new ProjectInviteMetadata();
        metadata.setProjectId(project.getId());
        metadata.setProjectTitle(project.getTitle());
        metadata.setOwnerEmail(sender.getEmail());
        notification.setMetadata(metadata);
        return notification;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<NotificationDTO> getUnseenNotifications(String email) {
        return notificationMapper.toDtoList(notificationRepo.findByReceiverEmailAndSeenFalse(email));
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public long markAsSeen(long notificationId) {
        Optional<Notification> notificationOpt = notificationRepo.findById(notificationId);
        Notification notification;
        if (notificationOpt.isEmpty())
            throw new NotificationNotFoundException(MessageConstants.NOTIFICATION_NOT_FOUND);
        notification = notificationOpt.get();
        notification.setSeen(true);
        return notificationRepo.save(notification).getId();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void sendIssueCreatedNotifications(IssueCreatedMetadata issueCreatedMetadata, Set<User> receivers) {
        User sender = userRepo.findByEmail(issueCreatedMetadata.getCreatorEmail());
        for (User receiver : receivers) {
            if (receiver == null) {
                throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND);
            }
            if (receiver.equals(sender))
                continue;
            Notification notification = new Notification();
            notification.setMessage(sender.getEmail() + " created a new issue in " + issueCreatedMetadata.getProjectTitle());
            notification.setSender(sender);
            notification.setReceiver(receiver);
            notification.setType(NotificationType.ISSUE_CREATED);
            notification.setMetadata(issueCreatedMetadata);

            NotificationDTO notificationDto = notificationMapper.toDto(notificationRepo.save(notification));
            simpMessagingTemplate.convertAndSendToUser(
                    receiver.getEmail(),
                    "/queue/notifications",
                    notificationDto
            );
        }
    }
}
