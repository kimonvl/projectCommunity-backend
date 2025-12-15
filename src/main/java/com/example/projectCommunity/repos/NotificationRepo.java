package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Notification} entities.
 *
 * <p>Provides data access operations for notification persistence including:</p>
 * <ul>
 *     <li>Retrieving the unseen notifications of a specific user</li>
 * </ul>
 * */
public interface NotificationRepo extends JpaRepository<Notification, Long> {

    /**
     * Retrieves the unseen notifications of a user based on user's email.
     *
     * @param email the target user's email
     * @return the List of notification entities, of empty list if not found any
     * */
    List<Notification> findByReceiverEmailAndSeenFalse(String email);
}
