package com.example.asyncevents.handler;

import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskType;
import com.example.asyncevents.service.DataProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataProcessingHandler implements TaskHandler {

    private final DataProcessingService dataProcessingService;

    @Override
    public TaskType getTaskType() {
        return TaskType.DATA_PROCESSING;
    }

    @Override
    public void process(Task task) {

        dataProcessingService.processData(
                task.getPayload()
        );
    }
}