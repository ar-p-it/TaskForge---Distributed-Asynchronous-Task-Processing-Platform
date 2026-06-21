package com.example.asyncevents.service.impl;

import com.example.asyncevents.dto.request.CreateTaskRequest;
import com.example.asyncevents.dto.response.TaskResponse;
import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskStatus;
import com.example.asyncevents.repository.TaskRepository;
import com.example.asyncevents.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {

        Task task = Task.builder()
                .type(request.getType())
                .payload(request.getPayload())
                .status(TaskStatus.PENDING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Task savedTask = taskRepository.save(task);

        return TaskResponse.builder()
                .id(savedTask.getId())
                .type(savedTask.getType())
                .status(savedTask.getStatus())
                .build();
    }
}