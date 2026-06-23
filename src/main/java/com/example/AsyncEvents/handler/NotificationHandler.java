package com.example.asyncevents.handler;

import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationHandler
        implements TaskHandler {

    @Override
    public TaskType getTaskType() {
        return TaskType.NOTIFICATION;
    }

    @Override
    public void process(Task task) {

        log.info(
                "Processing notification task {}",
                task.getId());
    }
}