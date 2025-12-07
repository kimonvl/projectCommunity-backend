package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.CommentDTO;
import com.example.projectCommunity.models.comment.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {
    CommentDTO toDto(Comment comment);
    List<CommentDTO> toDtoList(List<Comment> comments);
}
