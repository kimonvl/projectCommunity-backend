package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
