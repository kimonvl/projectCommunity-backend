package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload used to create an issue in a project.
 *
 * <p>This DTO carries the identifier of the target project and the details of the issue.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIssueRequest {
    /** Title of the issue */
    private String title;
    /** Description of the issue */
    private String description;
    /** Identifier of the target project. */
    private long projectId;
}
