package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to log in a user with wrong email or password.
 * */
public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
    }
}
