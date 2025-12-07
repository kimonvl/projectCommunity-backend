package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.models.notification.IssueCreatedMetadata;
import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface NotificationService {
    ResponseEntity<ResponseDTO<List<NotificationDTO>>> getUnseenNotifications(String email);
    void markAsSeen(long notificationId) throws Exception;
    void sendProjectInviteNotification(List<User> receivers, User sender, Project project);
    void sendIssueCreatedNotification(IssueCreatedMetadata issueCreatedMetadata, Set<User> receivers);
}
