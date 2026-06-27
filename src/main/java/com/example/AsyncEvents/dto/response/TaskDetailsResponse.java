package com.example.asyncevents.dto.response;

import com.example.asyncevents.enums.TaskStatus;
// import com.example.asyncevents.enums.TaskType;
import com.example.task_contracts.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskDetailsResponse {

    private UUID id;

    private TaskType type;

    private String payload;

    private TaskStatus status;

    private int retryCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}