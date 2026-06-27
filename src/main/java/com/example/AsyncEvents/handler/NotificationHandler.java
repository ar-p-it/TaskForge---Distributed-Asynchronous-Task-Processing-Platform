package com.example.asyncevents.handler;

import com.example.asyncevents.entity.Task;
// import com.example.asyncevents.enums.TaskType;
import com.example.task_contracts.enums.TaskType;
import com.example.asyncevents.service.NotificationService;
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