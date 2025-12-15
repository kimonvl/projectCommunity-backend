package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to fetch a notification with
 * a non-existent identifier.
 * */
public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
