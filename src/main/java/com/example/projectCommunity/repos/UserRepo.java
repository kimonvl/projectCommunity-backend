package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    @Query("""
    SELECT u FROM User u
    WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))
      AND u.id NOT IN (
        SELECT p.id FROM Project pr JOIN pr.participants p WHERE pr.id = :projectId
      )
      AND u.id <> (SELECT pr.owner.id FROM Project pr WHERE pr.id = :projectId)
""")
    List<User> searchAvailableUsers(String query, long projectId);
    public List<User> findByEmailIn(List<String> emails);
}
