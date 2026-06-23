package com.example.asyncevents.handler;

import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataProcessingHandler
        implements TaskHandler {
    @Override
    public TaskType getTaskType() {
        return TaskType.DATA_PROCESSING;
    }

    @Override
    public void process(Task task) {

        log.info(
                "Processing data task {}",
                task.getId());
    }
}