package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.mappers.UserMapper;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserDTO> searchUsersByEmail(String emailQuery, long projectId) {
        List<User> users = userRepo.searchAvailableUsers(emailQuery, projectId);
        return  userMapper.toDtoList(users);
    }
}

