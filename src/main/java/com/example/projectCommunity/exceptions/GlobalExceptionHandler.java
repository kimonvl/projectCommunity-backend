package com.example.projectCommunity.exceptions;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler responsible for gathering and translating application-specific
 * exceptions into standardized http responses.
 *
 * <p>This class ensures consistent error responses by wrapping the
 * exception messages into {@link ResponseDTO} which is contained inside {@link ResponseEntity}
 * with appropriate http status codes.</p>
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where a user tries to register with an email
     * already associated with an existing account.
     *
     * @param exception the thrown {@link EmailAlreadyInUseException}
     * @return a response with a message indicating registration failure
     * */
    @ExceptionHandler(EmailAlreadyInUseException.class)
    ResponseEntity<ResponseDTO<?>> handleEmailInUse(EmailAlreadyInUseException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.IM_USED);
    }

    /**
     * Handles cases where the user tries to log in with wrong credentials.
     *
     * @param exception the thrown {@link BadCredentialsException}
     * @return a response with a message indicating the log in failure
     * */
    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ResponseDTO<?>> handleBadCredentials(BadCredentialsException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles cases where the user tries to log in with wrong credentials.
     * This exception is thrown by spring security.
     *
     * @param exception the thrown {@link AuthenticationException} by spring security
     * @return a response with a message indicating the log in failure
     * */
    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ResponseDTO<?>> handleBadCredentialsSpring(AuthenticationException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles cases where the user refers to a chat that doesn't exist.
     *
     * @param exception the thrown {@link ChatNotFoundException}
     * @return a response with a message indicating the absence of the chat resource
     * */
    @ExceptionHandler(ChatNotFoundException.class)
    ResponseEntity<ResponseDTO<?>> handleChatNotFound(ChatNotFoundException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles cases where the user refers to a notification that doesn't exist.
     *
     * @param exception the thrown {@link NotificationNotFoundException}
     * @return a response with a message indication the absence of the notification resource
     * */
    @ExceptionHandler(NotificationNotFoundException.class)
    ResponseEntity<ResponseDTO<?>> handleNotificationNotFound(NotificationNotFoundException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}
