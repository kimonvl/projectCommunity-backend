package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;
/**
 * Service interface defining authentication related operations like
 * register login and authentication check.
 * */
public interface AuthService {
    /**
     * Registers a user with provided email and password.
     *
     * @param user user credentials.
     * @return registered {@link UserDTO}.
     * */
    UserDTO register(User user);

    /**
     * Authenticates a user with email and password.
     *
     * @param user user credentials.
     * @return authenticated {@link UserDTO}.
     * */
    UserDTO login(User user);

    /**
     * Checks if user is authenticated.
     *
     * @param email user's email.
     * @return authenticated {@link UserDTO}.
     * */
    UserDTO isAuthenticated(String email);
}
