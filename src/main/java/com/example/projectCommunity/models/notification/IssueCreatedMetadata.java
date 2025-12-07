package com.example.projectCommunity.models.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueCreatedMetadata {
    private String creatorEmail;
    private long projectId;
    private long issueId;
    @JsonIgnore
    private String projectTitle;
}
