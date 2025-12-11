package com.example.projectCommunity.config.Filters;

import com.example.projectCommunity.services.JwtService;
import com.example.projectCommunity.services.JwtServiceImpl;
import com.example.projectCommunity.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Servlet filter responsible for extracting, validating the JWT token from incoming requests.
 *
 * <p>Runs once per request before the username and password authentication and uses
 * {@link JwtService} {@link MyUserDetailsService} to process and validate the token.
 * If it's valid set the security context with a validated {@link UsernamePasswordAuthenticationToken}</p>
 * */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * Processes the request attempting to extract and validate the jwt, granting access in case of a valid token,
     * not granting access and clearing the cookies in case of an invalid one.
     *
     * @param request      the incoming HTTP request
     * @param response     the HTTP response to modify if necessary
     * @param filterChain  the remaining filter chain to execute
     * @throws ServletException if the filter fails during processing
     * @throws IOException      if I/O errors occur during filtering
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = null;
        String email = null;

        try {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("jwt")) {
                        token = cookie.getValue();
                        email = jwtService.extractEmail(token);
                    }
                }
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    clearJwtCookie(response);
                }
            }

        } catch (Exception e) {
            clearJwtCookie(response);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Clears the JWT cookie from the client by issuing a Set-Cookie header
     * with an expired, empty cookie. Used when token validation fails.
     *
     * @param response the HTTP response to which the clearing cookie is added
     */
    private void clearJwtCookie(HttpServletResponse response) {
        ResponseCookie clearedCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, clearedCookie.toString());
    }
}
