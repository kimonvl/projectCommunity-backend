package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.models.project.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProjectMapper {
    ProjectDTO toDto(Project project);
    List<ProjectDTO> toDtoList(List<Project> projects);
}
