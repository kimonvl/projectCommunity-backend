package com.example.projectCommunity.models.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data object describing the context of a project invitation notification.
 *
 * <p>This object is serialized and stored as notification metadata
 * to provide additional information about the project invitation.</p>
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInviteMetadata {

    /** Identifier of the target project */
    private long projectId;

    /** Title of the target project */
    private String projectTitle;

    /** Email of the owner of the target project that sent the invitation */
    private String ownerEmail;
}
