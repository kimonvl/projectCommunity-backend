package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.services.AuthService;
import com.example.projectCommunity.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Rest controller responsible for the authentication endpoints. It handles
 * registration, login, logout, authentication state check.
 *
 * <p>Implements a stateless jwt authentication system by attaching jwt in response cookie during login.</p>
 * */
@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    JwtService jwtService;
//ToDo: change User to some kind of dto (RegisterLoginRequest)

    /**
     * Create a new account for user with given credentials.
     *
     * @param user email and password in a {@link User} object.
     * @return a successful {@link ResponseEntity} http response wrapping the registered {@link UserDTO}.
     * */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserDTO>> register(@RequestBody User user) {
        return ResponseFactory.createSuccessResponse(authService.register(user), MessageConstants.REGISTERED, HttpStatus.CREATED);
    }

    /**
     * Validates the credentials given from user and attaches a jwt in response cookies for
     * authenticating future requests
     *
     * @param user credentials from user.
     * @return a successful {@link ResponseEntity} http response wrapping the logged in {@link UserDTO}.
     * */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<UserDTO>> login(@RequestBody User user) {
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
        return ResponseFactory.createSuccessResponse(userDTO, MessageConstants.LOGGED_IN, HttpStatus.ACCEPTED, cookie);
    }

    /**
     * Logs out the user by clearing the jwt from his cookies.
     *
     * @return a successful {@link ResponseEntity} http response.
     * */
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
        return ResponseFactory.createSuccessResponse(null, MessageConstants.LOGGED_OUT, HttpStatus.ACCEPTED, cookie);
    }

    /**
     * Checks if user is authenticated
     *
     * @return a successful {@link ResponseEntity} http response wrapping the authenticated {@link UserDTO}.
     * */
    @GetMapping("/isAuthenticated")
    public ResponseEntity<ResponseDTO<UserDTO>> isAuthenticated(Principal principal) {
        return ResponseFactory.createSuccessResponse(authService.isAuthenticated(principal.getName()), MessageConstants.USER_IS_AUTHENTICATED, HttpStatus.ACCEPTED);
    }
}
