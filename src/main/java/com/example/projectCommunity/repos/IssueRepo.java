package com.example.projectCommunity.repos;

import com.example.projectCommunity.models.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepo extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(long projectId);
}
