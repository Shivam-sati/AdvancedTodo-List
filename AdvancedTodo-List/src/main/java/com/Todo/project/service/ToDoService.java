package com.Todo.project.service;

import com.Todo.project.entity.ToDo;
import java.util.List;
import java.util.Optional;

public interface ToDoService {
    List<ToDo> getAllTodos();
    Optional<ToDo> getTodoById(String id);
    ToDo saveTodo(ToDo todo);
    void deleteTodo(String id);
    ToDo toggleTodoStatus(String id);
}
