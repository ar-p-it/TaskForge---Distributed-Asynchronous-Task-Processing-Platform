package com.example.asyncevents.service.impl;

import com.example.asyncevents.dto.request.CreateTaskRequest;
import com.example.asyncevents.dto.response.DashboardResponse;
import com.example.asyncevents.dto.response.FailedTaskResponse;
import com.example.asyncevents.dto.response.TaskDetailsResponse;
import com.example.asyncevents.dto.response.TaskResponse;
import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskStatus;
import com.example.asyncevents.repository.TaskRepository;
import com.example.asyncevents.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

// day 2 
// import com.example.asyncevents.event.TaskCreatedEvent;
import com.example.task_contracts.event.TaskCreatedEvent;
import com.example.asyncevents.producer.TaskProducer;
import com.example.asyncevents.dto.response.TaskStatsResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

        // private final TaskRepository taskRepository;
        // day 2chnages
        private final TaskRepository taskRepository;
        private final TaskProducer taskProducer;

        @Override
        public TaskResponse createTask(CreateTaskRequest request) {
                log.info("Received task creation request. Type={}", request.getType());

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
                // .id(savedTask.getId())
                // .type(savedTask.getType())
                // .status(savedTask.getStatus())
                // .build();
                // day 2
                Task savedTask = taskRepository.save(task);
                log.info("Task {} saved with status {}",
                                savedTask.getId(),
                                savedTask.getStatus());

                log.info("Publishing TaskCreatedEvent for task {}",
                                savedTask.getId());

                taskProducer.publishTaskCreated(
                                new TaskCreatedEvent(savedTask.getId()));
                log.info("Task {} handed off for asynchronous processing",
                                savedTask.getId());
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
                                failed);
        }

        @Override
        public List<FailedTaskResponse> getFailedTasks() {

                return taskRepository.findByStatus(
                                TaskStatus.FAILED)
                                .stream()
                                .map(task -> new FailedTaskResponse(
                                                task.getId(),
                                                task.getType(),
                                                task.getStatus(),
                                                task.getRetryCount()))
                                .collect(Collectors.toList());
        }

        @Override
        public DashboardResponse getDashboard() {

                long totalTasks = taskRepository.count();

                long completedTasks = taskRepository.countByStatus(
                                TaskStatus.COMPLETED);

                long failedTasks = taskRepository.countByStatus(
                                TaskStatus.FAILED);

                long pendingTasks = taskRepository.countByStatus(
                                TaskStatus.PENDING);

                long processingTasks = taskRepository.countByStatus(
                                TaskStatus.PROCESSING);

                double successRate = 0;
                double failureRate = 0;

                if (totalTasks > 0) {

                        // successRate = (completedTasks * 100.0) / totalTasks;

                        // failureRate = (failedTasks * 100.0) / totalTasks;
                        successRate = Math.round(
                                        (completedTasks * 100.0 / totalTasks) * 100) / 100.0;

                        failureRate = Math.round(
                                        (failedTasks * 100.0 / totalTasks) * 100) / 100.0;
                }

                return new DashboardResponse(
                                totalTasks,
                                completedTasks,
                                failedTasks,
                                pendingTasks,
                                processingTasks,
                                successRate,
                                failureRate);
        }

        @Override
        public TaskDetailsResponse getTaskById(UUID id) {

                Task task = taskRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Task not found"));

                return new TaskDetailsResponse(
                                task.getId(),
                                task.getType(),
                                task.getPayload(),
                                task.getStatus(),
                                task.getRetryCount(),
                                task.getCreatedAt(),
                                task.getUpdatedAt());
        }
}