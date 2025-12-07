package com.example.projectCommunity.exceptions;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    ResponseEntity<ResponseDTO<?>> handleEmailInUse(EmailAlreadyInUseException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.IM_USED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ResponseDTO<?>> handleBadCredentials(BadCredentialsException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ResponseDTO<?>> handleBadCredentialsSpring(AuthenticationException exception){
        return new ResponseEntity<>(new ResponseDTO<>(null, exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}
