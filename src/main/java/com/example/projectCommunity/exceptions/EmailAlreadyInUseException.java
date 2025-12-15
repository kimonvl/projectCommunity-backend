package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to register a user with
 * an email associated with an already existing account.
 * */
public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
