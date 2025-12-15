package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing {@link User} entities.
 *
 * <p>Provides data access operations for user persistence, including:</p>
 *     <ul>
 *         <li>Retrieving the user by email</li>
 *         <li>Searching for users who are not participants in a given project</li>
 *         <li>Retrieving the users whose emails match a provided list</li>
 *     </ul>
 * */
public interface UserRepo extends JpaRepository<User, Long> {

    /**
     * Retrieves a user based on a given email.
     *
     * @param email user's email address
     * @return the user entity, or {@code null} if not found
     * */
    public User findByEmail(String email);

    /**
     * Searches the users that:
     *     <ul>
     *         <li>Are not participants of a given project</li>
     *         <li>Their email address matches a given string</li>
     *     </ul>
     *
     * @param query the string that the email address has to match
     * @param projectId the identifier of the project whose participants need to be excluded
     * @return the List of user entities, or empty list if not found any
     * */
    @Query("""
    SELECT u FROM User u
    WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))
      AND u.id NOT IN (
        SELECT p.id FROM Project pr JOIN pr.participants p WHERE pr.id = :projectId
      )
      AND u.id <> (SELECT pr.owner.id FROM Project pr WHERE pr.id = :projectId)
""")
    List<User> searchAvailableUsers(String query, long projectId);

    /**
     * Retrieves the users whose email addresses match a given list.
     *
     * @param emails the List of emails to be matched
     * @return the List of users, or empty list if not found any
     * */
    public List<User> findByEmailIn(List<String> emails);
}
