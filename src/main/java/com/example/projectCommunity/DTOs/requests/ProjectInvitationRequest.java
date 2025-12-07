package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInvitationRequest {
    private List<String> receiverEmails;
    private long projectId;
}
