package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
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
        return ResponseFactory.createSuccessResponse(issueService.createIssue(createIssueRequest, principal.getName()), "Issue created", HttpStatus.CREATED);
    }

    @GetMapping("/getProjectIssues/{projectId}")
    ResponseEntity<ResponseDTO<List<IssueDTO>>> getProjectIssues(@PathVariable long projectId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.getProjectIssues(projectId, principal.getName()), "Issues fetched", HttpStatus.ACCEPTED);
    }

    @PostMapping("/assignUser")
    ResponseEntity<ResponseDTO<UserDTO>> assignUser(@RequestBody AssignUserToIssueRequest assignUserToIssueRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.assignUser(assignUserToIssueRequest, principal.getName()), "User assigned to issue", HttpStatus.CREATED);
    }

    @GetMapping("/getIssueDetails/{issueId}")
    ResponseEntity<ResponseDTO<IssueDTO>> getIssueDetails(@PathVariable long issueId, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.getIssueDetails(issueId, principal.getName()), "Issue fetched", HttpStatus.ACCEPTED);
    }

    @PostMapping("/changeStatus")
    ResponseEntity<ResponseDTO<IssueDTO>> changeStatus(@RequestBody ChangeIssueStatusRequest changeIssueStatusRequest, Principal principal) {
        return ResponseFactory.createSuccessResponse(issueService.changeStatus(changeIssueStatusRequest, principal.getName()), "Status changed", HttpStatus.CREATED);
    }
}
