package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.models.chat.Chat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MessageMapper.class, UserMapper.class})
public interface ChatMapper {
    ChatDTO toDto(Chat chat);
}
