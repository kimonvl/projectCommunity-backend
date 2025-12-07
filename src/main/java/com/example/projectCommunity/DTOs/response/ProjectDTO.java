package com.example.projectCommunity.DTOs.response;

import com.example.projectCommunity.models.project.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private long id;
    private String title;
    private ProjectCategory category;
    private String description;
    private List<String> tags;
    private UserDTO owner;
    private Set<UserDTO> participants;
}
