package com.example.projectCommunity.exceptions;

/**
 * Exception thrown when attempting to fetch a project with
 * a non-existent identifier.
 * */
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
