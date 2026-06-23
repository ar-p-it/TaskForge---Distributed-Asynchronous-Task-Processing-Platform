package com.example.asyncevents.handler;

import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskType;

public interface TaskHandler {
    TaskType getTaskType();

    void process(Task task);
}