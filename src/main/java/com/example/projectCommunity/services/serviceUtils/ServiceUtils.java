package com.example.projectCommunity.services.serviceUtils;

import com.example.projectCommunity.exceptions.UserNotParticipantException;
import com.example.projectCommunity.repos.ProjectRepo;

public class ServiceUtils {
    public static void checkAccessToProject(ProjectRepo projectRepo, long projectId, String email, String message) {
        Boolean hasAccess = projectRepo.userHasAccessToProject(projectId, email);
        if (hasAccess == null || !hasAccess) {
            throw new UserNotParticipantException(message);
        }
    }
}
