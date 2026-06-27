package com.example.worker_service.handler;

import com.example.worker_service.entity.Task;
// import com.example.worker_service.enums.TaskType;
import com.example.task_contracts.enums.TaskType;
import com.example.worker_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationHandler implements TaskHandler {

    private final NotificationService notificationService;

    @Override
    public TaskType getTaskType() {
        return TaskType.NOTIFICATION;
    }

    @Override
    public void process(Task task) {

        notificationService.sendNotification(
                task.getPayload()
        );
    }
}