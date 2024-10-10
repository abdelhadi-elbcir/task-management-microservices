package com.task.taskmanagement;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    private LocalDateTime dueDate;
    
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    private UUID userId;

}