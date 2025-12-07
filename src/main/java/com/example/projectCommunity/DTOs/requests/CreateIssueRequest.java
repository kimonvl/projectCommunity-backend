package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIssueRequest {
    private String title;
    private String description;
    private long projectId;
}
