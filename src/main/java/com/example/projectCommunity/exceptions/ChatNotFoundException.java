package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to fetch a chat resource with
 * a non-existent identifier.
 * */
public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(String message) {
        super(message);
    }
}
