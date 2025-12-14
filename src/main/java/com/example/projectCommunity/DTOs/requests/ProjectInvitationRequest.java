package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request payload used to send project invitation to a group of users.
 *
 * <p>This DTO carries the emails of the invited users and,
 * the identifier of the target project.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInvitationRequest {
    /** Emails of the users to be invited */
    private List<String> receiverEmails;
    /** Identifier of the target project */
    private long projectId;
}
