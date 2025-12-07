package com.example.projectCommunity.services;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IssueService {
    ResponseEntity<ResponseDTO<IssueDTO>> createIssue(CreateIssueRequest createIssueRequest, String email);
    ResponseEntity<ResponseDTO<List<IssueDTO>>> getProjectIssues(long projectId, String email);
    ResponseEntity<ResponseDTO<UserDTO>> assignUser(AssignUserToIssueRequest assignUserToIssueRequest, String email);
    ResponseEntity<ResponseDTO<IssueDTO>> getIssueDetails(long issueId, String email);
    ResponseEntity<ResponseDTO<IssueDTO>> changeStatus(ChangeIssueStatusRequest changeIssueStatusRequest, String email);
}
