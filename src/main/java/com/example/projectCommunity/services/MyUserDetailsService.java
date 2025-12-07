package com.example.projectCommunity.services;

import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.models.user.UserPrincipal;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("Email not found");

        return new UserPrincipal(user);
    }
}
