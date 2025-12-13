package com.example.projectCommunity.controllers.controllerUtils;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import org.springframework.http.*;

/**
 * Factory utility responsible for creating http responses with the data
 * wrapped in {@link ResponseDTO}.
 *
 * <p>This class provides to the controllers a uniform way to construct the http responses.</p>
 * */
public class ResponseFactory {
    private ResponseFactory() {
        // Prevent instantiation
    }

    /**
     * Creates a successful http response without any additional cookie in header.
     *
     * @param <T> the type of the response payload.
     * @param data the data to be wrapped in {@link ResponseDTO}.
     * @param message the message to be wrapped in {@link ResponseDTO}.
     * @param status the status of the http {@link ResponseEntity}.
     * @return a {@link ResponseEntity} containing the {@link ResponseDTO}.
     * */
    public static <T> ResponseEntity<ResponseDTO<T>> createSuccessResponse(T data, String message, HttpStatus status) {
        return createSuccessResponse(data, message, status, null);
    }

    /**
     * Creates a successful http response with an additional cookie in header.
     *
     * @param <T> the type of the response payload.
     * @param data the data to be wrapped in {@link ResponseDTO}.
     * @param message the message to be wrapped in {@link ResponseDTO}.
     * @param status the status of the http {@link ResponseEntity}.
     * @param cookie the cookie to be attached.
     * @return a {@link ResponseEntity} containing the {@link ResponseDTO}.
     * */
    public static <T> ResponseEntity<ResponseDTO<T>> createSuccessResponse(T data, String message, HttpStatus status, ResponseCookie cookie) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(status);
        if (cookie != null) {
            builder.header(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return builder.body(new ResponseDTO<>(data, message, true));
    }
}
