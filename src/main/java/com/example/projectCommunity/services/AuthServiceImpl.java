package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.mappers.UserMapper;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public ResponseEntity<ResponseDTO<UserDTO>> register(User user) {
        User existingUser = userRepo.findByEmail(user.getEmail());
        UserDTO userDTO = new UserDTO();
        if (existingUser != null) {
            return new ResponseEntity<>(new ResponseDTO<>(null, "Email already in use", false), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User newUSer = userRepo.save(user);
        userDTO = userMapper.toDto(newUSer);
        return new ResponseEntity<>(new ResponseDTO<>(userDTO, "User registered successfully", true), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<ResponseDTO<UserDTO>> login(User user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (auth.isAuthenticated()) {
            UserDTO userDTO = new UserDTO();
            User existingUser = userRepo.findByEmail(user.getEmail());
            String jwt = jwtService.generateToken(user.getEmail());
            userDTO = userMapper.toDto(existingUser);
            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(24*60*60)
                    .sameSite("None")
                    .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new ResponseDTO<>(userDTO, "Logged in successfully", true));
        }
        return new ResponseEntity<>(new ResponseDTO<>(null, "Wrong email or password", false), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<ResponseDTO<UserDTO>> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ResponseDTO<>(null, "Logged out successfully", true));
    }

    @Override
    public ResponseEntity<ResponseDTO<UserDTO>> isAuthenticated(String email) {
        User user = userRepo.findByEmail(email);
        UserDTO userDTO = new UserDTO();
        if (user == null)
            return new ResponseEntity<>(new ResponseDTO<>(null, "User not found", false), HttpStatus.BAD_REQUEST);
        userDTO = userMapper.toDto(user);
        return new ResponseEntity<>(new ResponseDTO<>(userDTO, "User is authenticated", true), HttpStatus.ACCEPTED);
    }
}
