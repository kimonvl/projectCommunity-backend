package com.example.projectCommunity.DTOs.response;

import com.example.projectCommunity.models.issue.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    private long id;
    private String title;
    private String description;
    private IssueStatus status;
    private UserDTO creator;
    private long projectId;
    private List<UserDTO> assignedUsers;
}
