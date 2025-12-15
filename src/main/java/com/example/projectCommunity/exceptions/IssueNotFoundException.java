package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to fetch an issue with
 * a non-existent identifier.
 * */
public class IssueNotFoundException extends RuntimeException {
    public IssueNotFoundException(String message) {
        super(message);
    }
}
