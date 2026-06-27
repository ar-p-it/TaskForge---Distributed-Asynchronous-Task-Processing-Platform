package com.example.worker_service.handler;

import com.example.worker_service.entity.Task;
// import com.example.worker_service.enums.TaskType;
import com.example.task_contracts.enums.TaskType;
import com.example.worker_service.service.ThirdPartyApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThirdPartyApiHandler implements TaskHandler {

    private final ThirdPartyApiService thirdPartyApiService;

    @Override
    public TaskType getTaskType() {
        return TaskType.THIRD_PARTY_API;
    }

    @Override
    public void process(Task task) {

        thirdPartyApiService.callApi(
                task.getPayload());
    }
}