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


// day 2 
import com.example.asyncevents.event.TaskCreatedEvent;
import com.example.asyncevents.producer.TaskProducer;
import com.example.asyncevents.dto.response.TaskStatsResponse;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    // private final TaskRepository taskRepository;
    // day 2chnages
private final TaskRepository taskRepository;
private final TaskProducer taskProducer;

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

        // Task savedTask = taskRepository.save(task);

        // return TaskResponse.builder()
        //         .id(savedTask.getId())
        //         .type(savedTask.getType())
        //         .status(savedTask.getStatus())
        //         .build();
        // day 2
        Task savedTask = taskRepository.save(task);

taskProducer.publishTaskCreated(
        new TaskCreatedEvent(savedTask.getId())
);

return TaskResponse.builder()
        .id(savedTask.getId())
        .type(savedTask.getType())
        .status(savedTask.getStatus())
        .build();
    }

@Override
public TaskStatsResponse getTaskStats() {

    long pending = taskRepository.countByStatus(
            TaskStatus.PENDING);

    long processing = taskRepository.countByStatus(
            TaskStatus.PROCESSING);

    long completed = taskRepository.countByStatus(
            TaskStatus.COMPLETED);

    long failed = taskRepository.countByStatus(
            TaskStatus.FAILED);

    return new TaskStatsResponse(
            pending,
            processing,
            completed,
            failed
    );
}

}