package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Issue} entities.
 *
 * <p>Provides data access operations for issue persistence including:</p>
 * <ul>
 *     <li>Retrieving based on the associated project's identifier</li>
 * </ul>
 * */
public interface IssueRepo extends JpaRepository<Issue, Long> {

    /**
     * Retrieves a List of issues based on the associated project's identifier.
     *
     * @param projectId the associated project's identifier
     * @return the List of issue entities, or empty list if not found any
     * */
    List<Issue> findByProjectId(long projectId);
}
