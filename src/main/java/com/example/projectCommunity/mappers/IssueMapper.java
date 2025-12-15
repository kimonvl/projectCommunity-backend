package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.models.issue.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper responsible for converting {@link Issue} entities
 * into {@link IssueDTO} objects.
 *
 * <p>This mapper is used to transform comment persistence models
 * into API-facing data transfer objects.</p>
 * */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface IssueMapper {

    /**
     * Converts a {@link Issue} into a {@link IssueDTO}.
     *
     * @param issue the issue entity to convert
     * @return the mapped issue DTO
     * */
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    IssueDTO toDto(Issue issue);

    /**
     * Converts a List of {@link Issue}s into a List of {@link IssueDTO}s.
     *
     * @param issues the List of issue entities to convert
     * @return the List of mapped issue DTOs
     * */
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    List<IssueDTO> toDtoList(List<Issue> issues);
}
