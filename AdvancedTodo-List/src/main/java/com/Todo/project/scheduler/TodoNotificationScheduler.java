package com.Todo.project.scheduler;

import com.Todo.project.entity.ToDo;
import com.Todo.project.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TodoNotificationScheduler {

    @Autowired
    private ToDoService todoService;

    @Autowired
    private JavaMailSender mailSender;

    // Check every minute
    @Scheduled(fixedRate = 60000)
    public void checkTodosForNotification() {
        List<ToDo> todos = todoService.getAllTodos();
        LocalDateTime now = LocalDateTime.now();

        for (ToDo todo : todos) {
            if (!todo.isNotificationSent() && todo.getDueTime() != null) {
                Duration duration = Duration.between(now, todo.getDueTime());
                // If the todo is still upcoming and has 15 minutes or less remaining
                if (!duration.isNegative() && duration.toMinutes() <= 15) {
                    sendNotificationEmail(todo);
                    todo.setNotificationSent(true);
                    todoService.saveTodo(todo);
                }
            }
        }
    }

    private void sendNotificationEmail(ToDo todo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(todo.getUserEmail());
        message.setSubject("Todo Reminder: " + todo.getTodoItem());
        message.setText("Hi " + todo.getUserName() + ",\n\n"
                + "This is a reminder that your todo: '" + todo.getTodoItem() 
                + "' is due in 15 minutes.\n\nBest regards,\nYour Todo App");
        mailSender.send(message);
    }
}
