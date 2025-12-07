package com.example.projectCommunity.models.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInviteMetadata {
    private long projectId;
    private String projectTitle;
    private String ownerEmail;
}
