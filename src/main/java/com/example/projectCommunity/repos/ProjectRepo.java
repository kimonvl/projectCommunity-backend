package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing {@link Project} entities.
 *
 * <p>Provides data access operations for project persistence including:</p>
 * <ul>
 *     <li>Retrieving the projects that a user participates in</li>
 *     <li>Checking if a user is participant in a project</li>
 * </ul>
 * */
public interface ProjectRepo extends JpaRepository<Project, Long> {

    /**
     * Retrieves the projects that a user participates in based on his email.
     *
     * @param email the target user's email
     * @return the List of project entities, or empty list if not found any
     * */
    public List<Project> findByParticipantsEmail(String email);

    /**
     * Checks if a user is participant in a project
     * based on his email and the project's identifier.
     *
     * @param email the user's email
     * @param projectId the target project's identifier
     * @return {@code true} if user is participant
     * */
    @Query("""
        SELECT CASE\s
            WHEN p.owner.email = :email THEN true
            WHEN EXISTS (
                SELECT u FROM p.participants u WHERE u.email = :email
            ) THEN true
            ELSE false
        END
        FROM Project p
        WHERE p.id = :projectId
   \s""")
    Boolean userHasAccessToProject(long projectId, String email);

}
