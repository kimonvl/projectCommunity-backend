package com.example.projectCommunity.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object representing a comment associated with an issue.
 *
 * <p>This DTO is returned by comment-related endpoints and contains the author and metadata of the comment,
 * and .</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    /** Unique identifier of the comment */
    private long id;
    /** Content of the comment */
    private String content;
    /** Author of the comment */
    private UserDTO author;
    /** Timestamp of comment creation */
    private LocalDateTime createdAt;
}
