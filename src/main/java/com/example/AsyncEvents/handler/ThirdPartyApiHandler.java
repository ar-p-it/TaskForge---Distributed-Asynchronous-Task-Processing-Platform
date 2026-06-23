package com.example.asyncevents.handler;

import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThirdPartyApiHandler
        implements TaskHandler {

    @Override
    public TaskType getTaskType() {
        return TaskType.THIRD_PARTY_API;
    }

    @Override
    public void process(Task task) {

        log.info(
                "Calling third party API for task {}",
                task.getId());
    }
}