package com.example.asyncevents.dto.response;

import com.example.asyncevents.enums.TaskStatus;
import com.example.asyncevents.enums.TaskType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class TaskResponse {

    private UUID id;
    private TaskType type;
    private TaskStatus status;
}