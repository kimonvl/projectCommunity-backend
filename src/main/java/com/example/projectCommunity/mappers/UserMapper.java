package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper responsible for converting {@link User} entities
 * into {@link UserDTO} objects.
 *
 * <p>This mapper is used to transform comment persistence models
 * into API-facing data transfer objects.</p>
 * */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a {@link User} into a {@link UserDTO}.
     *
     * @param user the user entity to convert
     * @return the mapped user DTO
     * */
    UserDTO toDto(User user);

    /**
     * Converts a List of {@link User}s into a List of {@link UserDTO}s.
     *
     * @param users the List of user entities to convert
     * @return the List of mapped user DTOs
     * */
    List<UserDTO> toDtoList(List<User> users);
}
