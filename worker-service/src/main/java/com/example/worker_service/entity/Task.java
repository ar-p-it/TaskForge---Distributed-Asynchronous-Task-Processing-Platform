package com.example.worker_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.task_contracts.enums.TaskType;
import com.example.task_contracts.event.TaskCreatedEvent;
// import com.example.asyncevents.enums.TaskStatus;
import com.example.worker_service.enums.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private Integer retryCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}