package com.Todo.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Todo.project.entity.ToDo;
import com.Todo.project.repository.ToDoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private ToDoRepository todoRepository;

    @Override
    public List<ToDo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<ToDo> getTodoById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    public ToDo saveTodo(ToDo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }

    @Override
    public ToDo toggleTodoStatus(String id) {
        ToDo toDo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        // Toggle status: if DONE, change to PENDING; otherwise, set to DONE
        toDo.setCompleted(toDo.getCompleted().equals("DONE") ? "PENDING" : "DONE");
        return todoRepository.save(toDo);
    }
}
