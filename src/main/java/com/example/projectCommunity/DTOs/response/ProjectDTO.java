package com.example.projectCommunity.DTOs.response;

import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.project.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * Data transfer object representing a {@link Project}.
 *
 * <p>This DTO is returned by project-related endpoints and contains the title,
 * the category, the description, the tags associated with the project,
 * the owner, and the participants in the project.</p>
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    /** Unique identifier of the project */
    private long id;
    /** Title of the project */
    private String title;
    /** Category of the project */
    private ProjectCategory category;
    /** Description of the project */
    private String description;
    /** Tags associated with the project */
    private List<String> tags;
    /** Owner of the project */
    private UserDTO owner;
    /** Participants of the project */
    private Set<UserDTO> participants;
}
