package com.example.projectCommunity.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

/**
 * Implementation of {@link JwtService}.
 * */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * {@inheritDoc}
     *
     * <p>Sets the user email address as the token subject and
     * configures an expiration time of 23 hours</p>
     * */
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofHours(23).toMillis()))
                .signWith(getKey())
                .compact();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Extracts the email from the token claims, verifies it against the provided user details,
     * and checks whether the token has expired.</p>
     * */
    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * {@inheritDoc}
     * */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all JWT claims from the provided token.
     *
     * <p>This method normalizes the token by removing an optional {@code Bearer }
     * prefix, verifies the token signature using the configured secret key,
     * and parses the signed claims payload.</p>
     *
     * @param token the raw JWT token, optionally prefixed with {@code Bearer }
     * @return the parsed {@link Claims} contained in the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid, malformed, or expired
     */
    private Claims extractAllClaims(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}

