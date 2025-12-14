package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload used to assign a user to an issue.
 *
 * <p>This DTO carries the identifiers required to assign a user to a project.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignUserToIssueRequest {
    /** Identifier of the target issue */
    public long issueId;
    /** Identifier of the user to be assigned */
    public long userId;
}
