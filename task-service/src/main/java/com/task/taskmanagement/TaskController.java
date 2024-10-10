package com.task.taskmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskEventPublisher taskEventPublisher;

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUserId(@PathVariable UUID userId) {
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        taskEventPublisher.publishTaskCreatedEvent(createdTask);
        return createdTask;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        taskEventPublisher.publishTaskUpdatedEvent(updatedTask);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        taskEventPublisher.publishTaskDeletedEvent(id);
        return ResponseEntity.noContent().build();
    }
}