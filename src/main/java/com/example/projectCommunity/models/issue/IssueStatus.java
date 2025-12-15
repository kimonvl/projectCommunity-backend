package com.example.projectCommunity.models.issue;

/**
 * Enumeration defining the possible statuses of an issue.
 * Refers to the level of progress done.
 * */
public enum IssueStatus {

    /** Work is not initiated yet. Default status of an issue that just created. */
    PENDING,

    /** Work is in progress but not completed yet. */
    IN_PROGRESS,

    /** Work is completed and issue is closed. */
    DONE
}
