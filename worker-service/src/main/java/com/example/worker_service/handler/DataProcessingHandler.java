package com.example.worker_service.handler;

import com.example.worker_service.entity.Task;
// import com.example.worker_service.enums.TaskType;
import com.example.task_contracts.enums.TaskType;
import com.example.worker_service.service.DataProcessingService;
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