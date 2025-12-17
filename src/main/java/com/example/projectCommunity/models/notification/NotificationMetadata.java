package com.example.projectCommunity.models.notification;

import com.example.projectCommunity.DTOs.response.NotificationDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Helper interface that is implemented from different types of notification metadata classes.
 * It provides a unified way to include any kind of notification metadata object in the {@link NotificationDTO}.
 * Then the JACKSON can convert it in the correct class depending on the notification type.
 * */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProjectInviteMetadata.class, name = "PROJECT_INVITE"),
        @JsonSubTypes.Type(value = IssueCreatedMetadata.class, name = "ISSUE_CREATED")
})
public interface NotificationMetadata {
}
