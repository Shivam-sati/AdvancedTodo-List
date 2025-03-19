package com.Todo.project.dto;

import lombok.Data;

@Data
public class ToDoDTO {
    private String todoItem;
    private String completed;
    private String userName;
    private String userEmail;
    // Optional: direct due time as an ISO string (e.g. "2023-05-01T14:30")
    private String dueTime;
    // Optional: duration in minutes (as a String) to compute due time from now
    private String durationInMinutes;
}
