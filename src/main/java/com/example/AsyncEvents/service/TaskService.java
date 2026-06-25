package com.example.asyncevents.service;

import com.example.asyncevents.dto.request.CreateTaskRequest;
import com.example.asyncevents.dto.response.TaskResponse;
import com.example.asyncevents.dto.response.TaskStatsResponse;
import java.util.List;

import com.example.asyncevents.dto.response.DashboardResponse;
import com.example.asyncevents.dto.response.FailedTaskResponse;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);

    List<FailedTaskResponse> getFailedTasks();

    TaskStatsResponse getTaskStats();

    DashboardResponse getDashboard();
}