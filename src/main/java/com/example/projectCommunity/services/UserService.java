package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.response.UserDTO;

import java.util.List;

/**
 * Service interface defining user related operations.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *     <li>Searching users based a given substring query</li>
 * </ul>
 * */
public interface UserService {

    /**
     * Retrieves the users whose email matches the query and,
     * they don't participate in a specific project.
     *
     * @param emailQuery string that the email address has to match
     * @param projectId the identifier of the project which participants need to be excluded from the search
     * @return the list of {@link UserDTO}s meeting the conditions
     * */
    List<UserDTO> searchUsersByEmail(String emailQuery, long projectId);
}
