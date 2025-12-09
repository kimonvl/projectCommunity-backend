package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;

import java.util.List;

public interface IssueService {
    IssueDTO createIssue(CreateIssueRequest createIssueRequest, String email);
    List<IssueDTO> getProjectIssues(long projectId, String email);
    UserDTO assignUser(AssignUserToIssueRequest assignUserToIssueRequest, String email);
    IssueDTO getIssueDetails(long issueId, String email);
    IssueDTO changeStatus(ChangeIssueStatusRequest changeIssueStatusRequest, String email);
}
