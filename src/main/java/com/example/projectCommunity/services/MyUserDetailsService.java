package com.example.projectCommunity.services;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.user.UserPrincipal;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom {@link UserDetailsService} implementation used by Spring Security
 * to load user specific data during authentication.
 *
 * <p>This service retrieves a {@link User} entity by email and adapts it
 * to Spring Security's {@link UserDetails} contract using {@link UserPrincipal}</p>
 * */
@Service
public class MyUserDetailsService implements UserDetailsService {

    // Repos
    @Autowired
    UserRepo userRepo;

    /**
     * Loads the user identified by the given username (email)
     *
     * @param username email address of the user
     * @return a fully populated {@link UserDetails} instance
     * @throws UsernameNotFoundException if the user doesn't exist
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("Email not found");

        return new UserPrincipal(user);
    }
}
