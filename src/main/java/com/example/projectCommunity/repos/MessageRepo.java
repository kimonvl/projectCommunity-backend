package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Message} entities.
 *
 * <p>Provides data access operations for message persistence.</p>
 * */
public interface MessageRepo extends JpaRepository<Message, Long> {
}
