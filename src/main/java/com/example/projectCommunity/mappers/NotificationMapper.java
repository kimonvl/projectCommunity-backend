package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.example.projectCommunity.models.notification.Notification;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper responsible for converting {@link Notification} entities
 * into {@link NotificationDTO} objects.
 *
 * <p>This mapper is used to transform comment persistence models
 * into API-facing data transfer objects.</p>
 * */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NotificationMapper {

    /**
     * Converts a {@link Notification} into a {@link NotificationDTO}.
     *
     * @param notification the notification entity to convert
     * @return the mapped notification DTO
     * */
    NotificationDTO toDto(Notification notification);

    /**
     * Converts a List of {@link Notification}s into a List of {@link NotificationDTO}s.
     *
     * @param notifications the List of notification entities to convert
     * @return the List of mapped notification DTOs
     * */
    List<NotificationDTO> toDtoList(List<Notification> notifications);
}
