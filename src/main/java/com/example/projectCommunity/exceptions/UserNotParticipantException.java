package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to access a resource associated with
 * a specific project that the user is not participant in and thus has no access to the resource.
 * */
public class UserNotParticipantException extends RuntimeException {
    public UserNotParticipantException(String message) {
        super(message);
    }
}
