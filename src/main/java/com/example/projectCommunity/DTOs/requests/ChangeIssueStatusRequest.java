package com.example.projectCommunity.DTOs.requests;

import com.example.projectCommunity.models.issue.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeIssueStatusRequest {
    private long issueId;
    private IssueStatus status;
}
