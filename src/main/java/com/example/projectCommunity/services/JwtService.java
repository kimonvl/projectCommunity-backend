package com.example.projectCommunity.services;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface defining JWT-based authentication operations.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *     <li>Generating a JWT token</li>
 *     <li>Validating a token</li>
 *     <li>Extracting the user email address from a token</li>
 * </ul>
 * */
public interface JwtService {

    /**
     * Generates a JWT token containing the user's email as the subject.
     *
     * @param email email address of the authenticated user
     * @return the jwt token with the email inside
     * */
    String generateToken(String email);

    /**
     * Validates a jwt token.
     *
     * @param token the jwt token to be validated
     * @param userDetails user credentials used for validation
     * @return {@code true} if the token is valid
     * */
    boolean validateToken(String token, UserDetails userDetails);

    /**
     * Extracts the email address from a hashed jwt token.
     *
     * @param token the token from which the email address will be extracted
     * @return the extracted email address
     * */
    String extractEmail(String token);
}
