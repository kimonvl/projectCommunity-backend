package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.constants.MessageConstants;
import com.example.projectCommunity.controllers.controllerUtils.ResponseFactory;
import com.example.projectCommunity.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/issue")
public class IssueController {
    @Autowired
    IssueService issueService;

    @PostMapping("/create")
    ResponseEntity<ResponseDTO<IssueDTO>> createIssue(@RequestBody CreateIssueRequest createIssueRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.createIssue(createIssueRequest, principal.getName()), MessageConstants.ISSUE_CREATED, HttpStatus.CREATED);
    }

    @GetMapping("/getProjectIssues/{projectId}")
    ResponseEntity<ResponseDTO<List<IssueDTO>>> getProjectIssues(@PathVariable long projectId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.getProjectIssues(projectId, principal.getName()), MessageConstants.ISSUES_FETCHED, HttpStatus.ACCEPTED);
    }

    @PostMapping("/assignUser")
    ResponseEntity<ResponseDTO<UserDTO>> assignUser(@RequestBody AssignUserToIssueRequest assignUserToIssueRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.assignUser(assignUserToIssueRequest, principal.getName()), MessageConstants.USER_ASSIGNED_TO_ISSUE, HttpStatus.CREATED);
    }

    @GetMapping("/getIssueDetails/{issueId}")
    ResponseEntity<ResponseDTO<IssueDTO>> getIssueDetails(@PathVariable long issueId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.getIssueDetails(issueId, principal.getName()), MessageConstants.ISSUE_FETCHED, HttpStatus.ACCEPTED);
    }

    @PostMapping("/changeStatus")
    ResponseEntity<ResponseDTO<IssueDTO>> changeStatus(@RequestBody ChangeIssueStatusRequest changeIssueStatusRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.changeStatus(changeIssueStatusRequest, principal.getName()), MessageConstants.ISSUE_STATUS_CHANGED, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    ResponseEntity<ResponseDTO<Long>> changeStatus(@RequestBody long issueId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.deleteIssue(issueId, principal.getName()), MessageConstants.ISSUE_DELETED, HttpStatus.CREATED);
    }
}
