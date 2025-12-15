package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.ChatDTO;
import com.example.projectCommunity.models.chat.Chat;
import org.mapstruct.Mapper;

/**
 * Mapper responsible for converting {@link Chat} entities
 * into {@link ChatDTO} objects.
 *
 * <p>This mapper is used to transform chat persistence models
 * into API-facing data transfer objects.</p>
 * */
@Mapper(componentModel = "spring", uses = {MessageMapper.class, UserMapper.class})
public interface ChatMapper {

    /**
     * Converts a {@link Chat} into a {@link ChatDTO}.
     *
     * @param chat the chat entity to convert
     * @return the mapped chat DTO
     * */
    ChatDTO toDto(Chat chat);
}
