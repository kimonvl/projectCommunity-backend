package com.example.projectCommunity.controllers;

import com.example.projectCommunity.DTOs.requests.AssignUserToIssueRequest;
import com.example.projectCommunity.DTOs.requests.ChangeIssueStatusRequest;
import com.example.projectCommunity.DTOs.requests.CreateIssueRequest;
import com.example.projectCommunity.DTOs.response.IssueDTO;
import com.example.projectCommunity.DTOs.response.ResponseDTO;
import com.example.projectCommunity.DTOs.response.UserDTO;
import com.example.projectCommunity.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return issueService.createIssue(createIssueRequest, principal.getName());
    }

    @GetMapping("/getProjectIssues/{projectId}")
    ResponseEntity<ResponseDTO<List<IssueDTO>>> getProjectIssues(@PathVariable long projectId, Principal principal) {
        return issueService.getProjectIssues(projectId, principal.getName());
    }

    @PostMapping("/assignUser")
    ResponseEntity<ResponseDTO<UserDTO>> assignUser(@RequestBody AssignUserToIssueRequest assignUserToIssueRequest, Principal principal) {
        return issueService.assignUser(assignUserToIssueRequest, principal.getName());
    }

    @GetMapping("/getIssueDetails/{issueId}")
    ResponseEntity<ResponseDTO<IssueDTO>> getIssueDetails(@PathVariable long issueId, Principal principal) {
        return issueService.getIssueDetails(issueId, principal.getName());
    }

    @PostMapping("/changeStatus")
    ResponseEntity<ResponseDTO<IssueDTO>> changeStatus(@RequestBody ChangeIssueStatusRequest changeIssueStatusRequest, Principal principal) {
        return issueService.changeStatus(changeIssueStatusRequest, principal.getName());
    }
}
