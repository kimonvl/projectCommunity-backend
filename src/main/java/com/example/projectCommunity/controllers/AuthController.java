package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.services.AuthService;
import com.example.projectCommunity.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    JwtService jwtService;
//ToDo: change User to some kind of dto (RegisterLoginRequest)
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserDTO>> register(@RequestBody User user) {
        return new ResponseEntity<>(new ResponseDTO<>(authService.register(user), "User registered", true), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<UserDTO>> login(@RequestBody User user) {
        System.out.println("Before login");
        UserDTO userDTO = authService.login(user);
        String jwt = jwtService.generateToken(userDTO.getEmail());
        System.out.println("after login");
        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24*60*60)
                .sameSite("None")
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ResponseDTO<>(userDTO, "Logged in successfully", true));
    }

    @PostMapping("/log-out")
    public ResponseEntity<ResponseDTO<Object>> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ResponseDTO<>(null, "Logged out successfully", true));
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<ResponseDTO<UserDTO>> isAuthenticated(Principal principal) {
        return new ResponseEntity<>(new ResponseDTO<>(authService.isAuthenticated(principal.getName()), "User is authenticated", true), HttpStatus.ACCEPTED);
    }
}
