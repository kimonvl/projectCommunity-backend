package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.project.Project;
import com.example.projectCommunity.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    public Project findByOwner(User owner);
    public List<Project> findByParticipantsEmail(String email);
    public Project findByChatId(long chatId);
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
