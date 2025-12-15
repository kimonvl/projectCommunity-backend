package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to fetch a user with
 * a non-existent identifier.
 * */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
