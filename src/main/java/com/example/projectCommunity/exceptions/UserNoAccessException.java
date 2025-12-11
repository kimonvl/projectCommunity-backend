package com.example.projectCommunity.exceptions;

public class UserNoAccessException extends RuntimeException {
    public UserNoAccessException(String message) {
        super(message);
    }
}
