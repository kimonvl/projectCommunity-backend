package com.example.projectCommunity.DTOs.requests;

import com.example.projectCommunity.models.project.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request payload used to create a project.
 *
 * <p>This DTO carries the details required to create a project.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequest {
    /** Title of the project */
    String title;
    /** Description of the project */
    String description;
    /** Category of the project */
    ProjectCategory category;
    /** Tags associated with the project */
    List<String> tags;
}
