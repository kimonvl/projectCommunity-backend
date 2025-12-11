package com.example.projectCommunity.config;

import com.example.projectCommunity.config.Filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring security configuration.
 *
 * <p>Configures the application's authorization, authentication, and CORS policies.
 * Integrates a stateless JWT based authentication through {@link JwtFilter} which is applied on every request.</p>
 * */
@Configuration
@EnableWebSecurity
public class Config {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Configures security filter chain by, adding CORS CSRF policies, disabling default login form,
     * adding endpoint authorization rules, and placing the {@link JwtFilter} in the filter chain.
     *
     * @param http {@link HttpSecurity} builder used to customize web security.
     * @return The configured {@link SecurityFilterChain} instance.
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable());
        http.cors(cors -> {});
        http.formLogin(form -> form.disable());
        http.logout(logout -> logout.disable());
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/register", "/login")
                .permitAll()
                .anyRequest()
                .authenticated());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Sets the authentication provider as {@link DaoAuthenticationProvider} for database authentication,
     * using {@link UserDetailsService} to fetch the data and
     * using {@link BCryptPasswordEncoder} to verify the password
     *
     * @return The configured authentication provider
     * */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * Exposes the {@link AuthenticationManager} used by the authentication process.
     *
     * @param config Authentication configuration.
     * @return The authentication manager that stores the authentication state.
     * @throws {@link Exception}
     * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines CORS configuration for the application, enabling cross-origin
     * requests from allowed frontend clients and permitting credentials as required
     * for JWT cookie-based authentication.
     *
     * @return the configured {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return  source;
    }
}
