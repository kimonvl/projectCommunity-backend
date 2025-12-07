package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverEmailAndSeenFalse(String email);
}
