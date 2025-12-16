package com.example.projectCommunity.services.serviceUtils;

import com.example.projectCommunity.exceptions.UserNotParticipantException;
import com.example.projectCommunity.repos.ProjectRepo;

/**
 * Utility class providing common helper methods used across service-layer components.
 *
 * <p>In order to reduce duplication this class centralizes reusable service logic such as:</p>
 * <ul>
 *     <li>Access control checks</li>
 * </ul>
 * */
public class ServiceUtils {
    private ServiceUtils() {
        // prevent instantiation
    }

    /**
     * Checks if a user has access to a project based on the user's email
     * and the project's identifier.
     *
     * @param projectRepo repository used to verify project access
     * @param projectId the target project's identifier
     * @param email the user's email whose access is checked
     * @param message error message if access is denied
     * @throws UserNotParticipantException if the user doesn't have access
     * */
    public static void checkAccessToProject(ProjectRepo projectRepo, long projectId, String email, String message) {
        Boolean hasAccess = projectRepo.userHasAccessToProject(projectId, email);
        if (hasAccess == null || !hasAccess) {
            throw new UserNotParticipantException(message);
        }
    }
}
