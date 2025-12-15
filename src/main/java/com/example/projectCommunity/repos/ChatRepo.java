package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Chat} entities.
 *
 * <p>Provides data access operations for chat persistence including:</p>
 * <ul>
 *     <li>Retrieving based on the associated project's identifier</li>
 * </ul>
 * */
public interface ChatRepo extends JpaRepository<Chat, Long> {

    /**
     * Retrieves a chat by it's unique identifier.
     *
     * @param chatId the identifier of the chat
     * @return the chat entity, or {@code null} if not found
     * */
    public Chat findById(long chatId);

    /**
     * Retrieves a chat by it's associated project's unique identifier.
     *
     * @param projectId the identifier of the associated project
     * @return the chat entity, or {@code null} if not found
     * */
    public Chat findByProjectId(long projectId);
}
