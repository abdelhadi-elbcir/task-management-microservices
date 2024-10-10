package com.task.taskmanagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository UserRepository;

    public List<User> getUsers() {
        return UserRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return UserRepository.findById(id);
    }

    public User createUser(User User) {
        User.setStatus(UserStatus.PENDING);
        return UserRepository.save(User);
    }

    public User updateUser(UUID id, User UserDetails) {
        User User = UserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        User.setName(UserDetails.getName());
        User.setPhone(UserDetails.getPhone());
        User.setStatus(UserDetails.getStatus());

        return UserRepository.save(User);
    }

    public void deleteUser(UUID id) {
        UserRepository.deleteById(id);
    }

}