package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<Chat, Long> {
    public Chat findById(long chatId);
    public Chat findByProjectId(long projectId);
}
