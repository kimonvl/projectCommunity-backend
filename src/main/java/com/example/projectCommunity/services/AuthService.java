package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResponseDTO<UserDTO>> register(User user);
    ResponseEntity<ResponseDTO<UserDTO>> login(User user);
    ResponseEntity<ResponseDTO<UserDTO>> logout();
    ResponseEntity<ResponseDTO<UserDTO>> isAuthenticated(String email);
}
