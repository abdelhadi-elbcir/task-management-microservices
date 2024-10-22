package com.task.taskmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUserId(UUID userId) {
        Boolean exist = restTemplate.postForObject("http://user-service", null, Boolean.class, userId);
        if (!exist)
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) throws HttpClientErrorException{
        String userServiceUrl = "http://user-service/api/users/check/{id}";
        try {
            Boolean userExists = restTemplate.getForObject(userServiceUrl, Boolean.class, task.getUserId());
            if (!userExists) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
            }

            task.setStatus(TaskStatus.PENDING);
            return taskRepository.save(task);

        } catch (HttpClientErrorException ex) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User service error: " + ex.getMessage());
        }
    }

    public Task updateTask(UUID id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setStatus(taskDetails.getStatus());

        return taskRepository.save(task);
    }

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}