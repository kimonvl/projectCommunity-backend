package com.example.projectCommunity.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload used to accept a project invitation.
 *
 * <p>This DTO carries the identifiers required to find the target project and,
 * and find the associated invitation notification.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptProjectInvitationRequest {
    /** Identifier of the target project */
    private long projectId;
    /** Identifier of the associated invitation notification */
    private long notificationId;
}
