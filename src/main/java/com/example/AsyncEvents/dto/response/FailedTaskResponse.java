package com.example.asyncevents.dto.response;

import com.example.asyncevents.enums.TaskStatus;
import com.example.asyncevents.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FailedTaskResponse {

    private UUID id;
    private TaskType type;
    private TaskStatus status;
    private int retryCount;
}