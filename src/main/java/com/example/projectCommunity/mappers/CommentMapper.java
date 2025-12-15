package com.example.projectCommunity.mappers;

import com.example.projectCommunity.DTOs.response.CommentDTO;
import com.example.projectCommunity.models.comment.Comment;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper responsible for converting {@link Comment} entities
 * into {@link CommentDTO} objects.
 *
 * <p>This mapper is used to transform comment persistence models
 * into API-facing data transfer objects.</p>
 * */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {

    /**
     * Converts a {@link Comment} into a {@link CommentDTO}.
     *
     * @param comment the comment entity to convert
     * @return the mapped comment DTO
     * */
    CommentDTO toDto(Comment comment);

    /**
     * Converts a List of {@link Comment}s into a List of {@link CommentDTO}s.
     *
     * @param comments the List of comment entities to convert
     * @return the List of mapped comment DTOs
     * */
    List<CommentDTO> toDtoList(List<Comment> comments);
}
