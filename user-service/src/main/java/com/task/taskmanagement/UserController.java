package com.task.taskmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService UserService;

    @Autowired
    private UserEventPublisher UserEventPublisher;

    @GetMapping
    public List<User> getUsersByUserId() {
        return UserService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> User = UserService.getUserById(id);
        if (User.isPresent()) {
            return ResponseEntity.ok(User.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public User createUser(@RequestBody User User) {
        User createdUser = UserService.createUser(User);
        UserEventPublisher.publishUserCreatedEvent(createdUser);
        return createdUser;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User UserDetails) {
        User updatedUser = UserService.updateUser(id, UserDetails);
        UserEventPublisher.publishUserUpdatedEvent(updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        UserService.deleteUser(id);
        UserEventPublisher.publishUserDeletedEvent(id);
        return ResponseEntity.noContent().build();
    }
}