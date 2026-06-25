package com.example.asyncevents.controller;

import com.example.asyncevents.dto.request.CreateTaskRequest;
import com.example.asyncevents.dto.response.FailedTaskResponse;
import com.example.asyncevents.dto.response.TaskResponse;
import com.example.asyncevents.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.asyncevents.dto.response.TaskStatsResponse;

import com.example.asyncevents.dto.response.FailedTaskResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request) {

        TaskResponse response = taskService.createTask(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<TaskStatsResponse> getTaskStats() {

        return ResponseEntity.ok(
                taskService.getTaskStats());
    }

    @GetMapping("/failed")
    public ResponseEntity<List<FailedTaskResponse>> getFailedTasks() {

        return ResponseEntity.ok(
                taskService.getFailedTasks());
    }
}