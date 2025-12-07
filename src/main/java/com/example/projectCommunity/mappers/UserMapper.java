package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    List<UserDTO> toDtoList(List<User> users);
}
