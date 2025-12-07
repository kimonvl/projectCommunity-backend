package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
//ToDo: change User to some kind of dto (RegisterLoginRequest)
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserDTO>> register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<UserDTO>> login(@RequestBody User user) {
        return authService.login(user);
    }

    @PostMapping("/log-out")
    public ResponseEntity<ResponseDTO<UserDTO>> logout() {
        return authService.logout();
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<ResponseDTO<UserDTO>> isAuthenticated(Principal principal) {
        return authService.isAuthenticated(principal.getName());
    }
}
