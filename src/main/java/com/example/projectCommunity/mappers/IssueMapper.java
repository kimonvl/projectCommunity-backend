package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.models.issue.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface IssueMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    IssueDTO toDto(Issue issue);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    List<IssueDTO> toDtoList(List<Issue> issues);
}
