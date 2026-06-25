package com.example.asyncevents.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private long totalTasks;

    private long completedTasks;

    private long failedTasks;

    private long pendingTasks;

    private long processingTasks;

    private double successRate;

    private double failureRate;
}