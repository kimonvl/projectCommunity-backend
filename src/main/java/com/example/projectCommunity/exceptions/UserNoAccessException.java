package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to fetch a resource that
 * the user has no access to.
 * */
public class UserNoAccessException extends RuntimeException {
    public UserNoAccessException(String message) {
        super(message);
    }
}
