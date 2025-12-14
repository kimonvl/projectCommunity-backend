package com.example.projectCommunity.DTOs.requests;

import com.example.projectCommunity.models.issue.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload used to change the status of an issue.
 *
 * <p>This DTO carries the identifier of the target issue and the new status.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeIssueStatusRequest {
    /** Identifier of the target issue */
    private long issueId;
    /** New status to be applied */
    private IssueStatus status;
}
