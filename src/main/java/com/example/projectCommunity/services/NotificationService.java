package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.models.notification.IssueCreatedMetadata;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;

import java.util.List;
import java.util.Set;

public interface NotificationService {
    List<NotificationDTO> getUnseenNotifications(String email);
    long markAsSeen(long notificationId);
    void sendProjectInviteNotification(List<User> receivers, User sender, Project project);
    void sendIssueCreatedNotification(IssueCreatedMetadata issueCreatedMetadata, Set<User> receivers);
}
