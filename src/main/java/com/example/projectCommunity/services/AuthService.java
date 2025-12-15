package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.models.user.User;
/**
 * Service interface defining authentication related operations.
 *
 * <p>This service is responsible for user registration, authentication,
 * and verification of authentication status</p>
 * */
public interface AuthService {
    /**
     * Registers a user with the provided credentials.
     *
     * @param user user credentials
     * @return the registered {@link UserDTO}
     * */
    UserDTO register(User user);

    /**
     * Authenticates a user with provided credentials.
     *
     * @param user user credentials
     * @return authenticated {@link UserDTO}
     * */
    UserDTO login(User user);

    /**
     * Verifies whether a user is authenticated.
     *
     * @param email user's email address
     * @return authenticated {@link UserDTO}
     * */
    UserDTO isAuthenticated(String email);
}
