package com.example.projectCommunity.controllers;

import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller responsible for {@link User} related activities.
 *
 * <p>It exposes endpoints responsible for searching registered users.
 * All activities are performed within the context of an authenticated user. It returns standardized API responses.</p>
 * */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * Searches for {@link User}s to invite in a {@link Project}.
     *
     * @param emailQuery the search parameter.
     * @param projectId identifier of the project to invite to.
     * @return http response containing the retrieved {@link List<UserDTO>}.
     * */
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> searchUsers(@RequestParam String emailQuery, @RequestParam long projectId) {
        return ResponseFactory.createSuccessResponse(userService.searchUsersByEmail(emailQuery, projectId), MessageConstants.USERS_FETCHED, HttpStatus.ACCEPTED);
    }
}
