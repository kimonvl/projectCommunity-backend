package com.example.projectCommunity.DTOs.requests;

import com.example.projectCommunity.models.project.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequest {
    String title;
    String description;
    ProjectCategory category;
    List<String> tags;
}
