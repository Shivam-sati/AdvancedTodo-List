package com.Todo.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.Todo.project.entity.ToDo;

@Repository
public interface ToDoRepository extends MongoRepository<ToDo, String> {
}
