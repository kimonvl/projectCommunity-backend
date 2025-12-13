package com.example.projectCommunity.controllers;

import com.example.projectCommunity.models.notification.Notification;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Rest controller responsible for {@link Notification} related activities
 * in the context of an authenticated and connected {@link User}.
 *
 * <p>It exposes endpoints responsible for retrieving the unseen notifications of a user,
 * marking a specific notification as seen.
 * All activities are performed within the context of an authenticated user. It returns standardized API responses.</p>
 * */
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * Retrieves the unseen {@link Notification}s of a user.
     *
     * @param principal the authenticated user's data, that is requesting the unseen notifications,  provided by Spring Security.
     * @return http response containing the retrieved list of {@link NotificationDTO}.
     * */
    @GetMapping("/getUnseenNotifications")
    ResponseEntity<ResponseDTO<List<NotificationDTO>>> getUnseenNotifications(Principal principal) {
        return ResponseFactory.createSuccessResponse(notificationService.getUnseenNotifications(principal.getName()), MessageConstants.NOTIFICATIONS_FETCHED, HttpStatus.ACCEPTED);
    }

    /**
     * Marks as seen a specific {@link Notification}.
     *
     * @param notificationId identifier of the notification to mark as seen.
     * @return http response containing the identifier of the marked as seen notification.
     * */
    @PostMapping("/markAsSeen")
    ResponseEntity<ResponseDTO<Long>> markAsSeen(@RequestBody long notificationId) {
        return ResponseFactory.createSuccessResponse(notificationService.markAsSeen(notificationId), MessageConstants.NOTIFICATION_MARKED_AS_SEEN, HttpStatus.CREATED);
    }

}
