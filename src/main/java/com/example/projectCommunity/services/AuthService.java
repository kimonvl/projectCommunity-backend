package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;

public interface AuthService {
    UserDTO register(User user);
    UserDTO login(User user);
    UserDTO isAuthenticated(String email);
}
