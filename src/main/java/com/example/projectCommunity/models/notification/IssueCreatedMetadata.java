package com.example.projectCommunity.models.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data object describing the context of an issue creation notification.
 *
 * <p>This object is serialized and stored as notification metadata
 * to provide additional information about the issue created.</p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueCreatedMetadata implements NotificationMetadata {

    /** Email of the creator of the issue */
    private String creatorEmail;

    /** Identifier of the project that the created issue belongs to */
    private long projectId;

    /** Identifier of the created issue */
    private long issueId;

    /** Title of the project that the created issue belongs to */
    @JsonIgnore
    private String projectTitle;
}
