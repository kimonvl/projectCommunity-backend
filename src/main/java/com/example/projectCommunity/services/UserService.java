package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<ResponseDTO<List<UserDTO>>> searchUsersByEmail(String emailQuery, long projectId);
}
