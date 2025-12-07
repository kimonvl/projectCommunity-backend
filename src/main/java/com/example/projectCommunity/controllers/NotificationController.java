package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.ProjectInvitationRequest;
import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getUnseenNotifications")
    ResponseEntity<ResponseDTO<List<NotificationDTO>>> getUnseenNotifications(Principal principal) {
        return notificationService.getUnseenNotifications(principal.getName());
    }

}
