package com.example.asyncevents.service;

import com.example.asyncevents.dto.request.CreateTaskRequest;
import com.example.asyncevents.dto.response.TaskResponse;
import com.example.asyncevents.dto.response.TaskStatsResponse;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);

    TaskStatsResponse getTaskStats();
}