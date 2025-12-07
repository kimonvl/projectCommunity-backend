package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.models.notification.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NotificationMapper {
    NotificationDTO toDto(Notification notification);
    List<NotificationDTO> toDtoList(List<Notification> notifications);
}
