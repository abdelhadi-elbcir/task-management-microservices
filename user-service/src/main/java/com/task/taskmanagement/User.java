package com.task.taskmanagement;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String phone;
    private String email;
    private String username;

    private String password;
    
    private UserStatus status;

}