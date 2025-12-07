package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptProjectInvitationRequest {
    private long projectId;
    private long notificationId;
}
