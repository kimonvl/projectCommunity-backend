package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.MessageDTO;
import com.example.projectCommunity.models.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {
    @Mapping(source = "chat.id", target = "chatId")
    MessageDTO toDto(Message message);
    @Mapping(source = "chat.id", target = "chatId")
    List<MessageDTO> toDtoList(List<Message> messages);
}
