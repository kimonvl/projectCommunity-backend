package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> searchUsers(@RequestParam String emailQuery, @RequestParam long projectId) {
        return userService.searchUsersByEmail(emailQuery, projectId);
    }
}
