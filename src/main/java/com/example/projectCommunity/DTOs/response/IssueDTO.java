package com.example.projectCommunity.DTOs.response;

import com.example.projectCommunity.models.issue.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data transfer object representing an issue associated with a project.
 *
 * <p>This DTO is returned by issue-related endpoints and contains the author ,
 * the assigned users, the identifier of the target project and, the issue metadata.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    /** Unique identifier of the issue */
    private long id;
    /** Title of the issue */
    private String title;
    /** Description of the issue*/
    private String description;
    /** Status of the issue */
    private IssueStatus status;
    /** Creator of the issue */
    private UserDTO creator;
    /** Identifier of the target project */
    private long projectId;
    /** Users assigned to the project*/
    private List<UserDTO> assignedUsers;
}
