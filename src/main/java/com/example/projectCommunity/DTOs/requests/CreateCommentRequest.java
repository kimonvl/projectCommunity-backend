package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload used to create a comment in an issue.
 *
 * <p>This DTO carries the identifier of the target issue and the content of the comment.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    /** Identifier of the target issue */
    private long issueId;
    /** Content of the comment */
    private String content;
}
