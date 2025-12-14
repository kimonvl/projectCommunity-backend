package com.example.projectCommunity.DTOs.response;

import com.example.projectCommunity.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object representing a {@link User}.
 *
 * <p>This DTO is returned by user-related endpoints and contains the user email.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    /** Unique identifier of the user */
    private long id;
    /** Email that the user registered with*/
    private String email;
}
