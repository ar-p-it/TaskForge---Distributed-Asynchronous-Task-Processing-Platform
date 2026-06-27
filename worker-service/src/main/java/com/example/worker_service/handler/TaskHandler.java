package com.example.worker_service.handler;

import com.example.worker_service.entity.Task;
// import com.example.worker_service.enums.TaskType;

import com.example.task_contracts.enums.TaskType;
public interface TaskHandler {
    TaskType getTaskType();

    void process(Task task);
}