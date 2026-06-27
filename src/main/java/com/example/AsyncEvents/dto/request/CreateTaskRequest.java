package com.example.asyncevents.dto.request;

// import com.example.asyncevents.enums.TaskType;
// import com.example.asyncevents.enums.TaskType;
import jakarta.validation.constraints.NotNull;
import com.example.task_contracts.enums.TaskType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRequest {

    @NotNull
    private TaskType type;

    @NotNull
    private String payload;
}