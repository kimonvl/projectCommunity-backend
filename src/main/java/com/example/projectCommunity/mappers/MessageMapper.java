package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.models.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper responsible for converting {@link Message} entities
 * into {@link MessageDTO} objects.
 *
 * <p>This mapper is used to transform comment persistence models
 * into API-facing data transfer objects.</p>
 * */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {

    /**
     * Converts a {@link Message} into a {@link MessageDTO}.
     *
     * @param message the message entity to convert
     * @return the mapped message DTO
     * */
    @Mapping(source = "chat.id", target = "chatId")
    MessageDTO toDto(Message message);

    /**
     * Converts a List of {@link Message}s into a List of {@link MessageDTO}s.
     *
     * @param messages the List of message entities to convert
     * @return the List of mapped message DTOs
     * */
    @Mapping(source = "chat.id", target = "chatId")
    List<MessageDTO> toDtoList(List<Message> messages);
}
