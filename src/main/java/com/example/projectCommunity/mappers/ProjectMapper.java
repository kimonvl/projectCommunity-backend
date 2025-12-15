package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.ProjectDTO;
import com.example.projectCommunity.models.project.Project;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper responsible for converting {@link Project} entities
 * into {@link ProjectDTO} objects.
 *
 * <p>This mapper is used to transform comment persistence models
 * into API-facing data transfer objects.</p>
 * */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProjectMapper {

    /**
     * Converts a {@link Project} into a {@link ProjectDTO}.
     *
     * @param project the project entity to convert
     * @return the mapped project DTO
     * */
    ProjectDTO toDto(Project project);

    /**
     * Converts a List of {@link Project}s into a List of {@link ProjectDTO}s.
     *
     * @param projects the List of project entities to convert
     * @return the List of mapped project DTOs
     * */
    List<ProjectDTO> toDtoList(List<Project> projects);
}
