package com.Todo.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "todos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {

    @Id
    private String id;

    private String todoItem;
    private String completed;
    
    // User details
    private String userName;
    private String userEmail;
    
    // Due time calculated either from an entered date/time or duration
    private LocalDateTime dueTime;
    
    // Flag to avoid sending duplicate notifications
    private boolean notificationSent;
}
