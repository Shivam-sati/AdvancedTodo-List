package com.Todo.project.controller;

import com.Todo.project.dto.ToDoDTO;
import com.Todo.project.entity.ToDo;
import com.Todo.project.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")  // Allow requests from your React frontend
public class ToDoRestController {

    @Autowired
    private ToDoService todoService;

    @GetMapping
    public List<ToDo> getTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public ToDo createTodo(@RequestBody ToDoDTO todoDTO) {
        ToDo todo = new ToDo();
        todo.setTodoItem(todoDTO.getTodoItem());
        todo.setCompleted(todoDTO.getCompleted());
        todo.setUserName(todoDTO.getUserName());
        todo.setUserEmail(todoDTO.getUserEmail());

        LocalDateTime computedDueTime = null;
        // If durationInMinutes is provided, use it to compute dueTime
        if (todoDTO.getDurationInMinutes() != null && !todoDTO.getDurationInMinutes().isEmpty()) {
            try {
                long minutes = Long.parseLong(todoDTO.getDurationInMinutes());
                computedDueTime = LocalDateTime.now().plusMinutes(minutes);
            } catch (NumberFormatException e) {
                computedDueTime = null;
            }
        } else if (todoDTO.getDueTime() != null && !todoDTO.getDueTime().isEmpty()) {
            try {
                computedDueTime = LocalDateTime.parse(todoDTO.getDueTime());
            } catch (DateTimeParseException e) {
                computedDueTime = null;
            }
        }
        todo.setDueTime(computedDueTime);
        todo.setNotificationSent(false);
        return todoService.saveTodo(todo);
    }

    @PutMapping("/{id}")
    public ToDo toggleTodo(@PathVariable String id) {
        return todoService.toggleTodoStatus(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
    }
}
