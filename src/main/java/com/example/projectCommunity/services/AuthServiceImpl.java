package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.exceptions.BadCredentialsException;
import com.example.projectCommunity.exceptions.EmailAlreadyInUseException;
import com.example.projectCommunity.exceptions.UserNotFoundException;
import com.example.projectCommunity.mappers.UserMapper;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link AuthService}.
 * */
@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * {@inheritDoc}
     * */
    @Override
    public UserDTO register(User user) {
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new EmailAlreadyInUseException(MessageConstants.EMAIL_ALREADY_IN_USE);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User newUSer = userRepo.save(user);
        return userMapper.toDto(newUSer);
    }

    @Override
    public UserDTO login(User user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (!auth.isAuthenticated()) {
           throw new BadCredentialsException(MessageConstants.WRONG_EMAIL_OR_PASSWORD);
        }
        User existingUser = userRepo.findByEmail(user.getEmail());
        return userMapper.toDto(existingUser);
    }

    @Override
    public UserDTO isAuthenticated(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND);
        return userMapper.toDto(user);
    }
}
