package com.example.projectCommunity.controllers.controllerUtils;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import org.springframework.http.*;

public class ResponseFactory {
    public static <T> ResponseEntity<ResponseDTO<T>> createSuccessResponse(T data, String message, HttpStatus status) {
        return createSuccessResponse(data, message, status, null);
    }

    public static <T> ResponseEntity<ResponseDTO<T>> createSuccessResponse(T data, String message, HttpStatus status, ResponseCookie cookie) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(status);
        if (cookie != null) {
            builder.header(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return builder.body(new ResponseDTO<>(data, message, true));
    }
}
