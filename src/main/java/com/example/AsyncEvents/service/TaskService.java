package com.example.asyncevents.service;

import com.example.asyncevents.dto.request.CreateTaskRequest;
import com.example.asyncevents.dto.response.TaskResponse;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);
}