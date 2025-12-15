package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.models.notification.IssueCreatedMetadata;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;

import java.util.List;
import java.util.Set;

/**
 * Service interface defining notification related operations.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *     <li>Retrieving the unseen notifications of a user</li>
 *     <li>Marking a notification as seen</li>
 *     <li>Sending a project invitation notification</li>
 *     <li>Sending an issue creation notification</li>
 * </ul>
 * */
public interface NotificationService {

    /**
     * Retrieve the unseen notifications of a user based on their email address.
     *
     * @param email email address of the user requesting the notifications
     * @return the list of the unseen {@link NotificationDTO}s
     * */
    List<NotificationDTO> getUnseenNotifications(String email);

    /**
     * Marks a notification as seen based on its identifier.
     *
     * @param notificationId the identifier of the target notification
     * @return the identifier of the marked notification as seen
     * */
    long markAsSeen(long notificationId);

    /**
     * Sends a project invitation notification to a list of users via WebSocket.
     *
     * @param receivers the list of the user entities that will receive the notification
     * @param sender the user entity that sends the notification
     * @param project the target project of the invitation
     * */
    void sendProjectInviteNotification(List<User> receivers, User sender, Project project);

    /**
     * Sends an issue created notification to a list of users via WebSocket.
     *
     * @param issueCreatedMetadata payload containing the issue identifier, the project identifier, the creator email and the title of the project
     * @param receivers the list of the user entities about to receive the notification
     * */
    void sendIssueCreatedNotifications(IssueCreatedMetadata issueCreatedMetadata, Set<User> receivers);
}
