package com.example.asyncevents.handler;

import com.example.asyncevents.enums.TaskType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TaskHandlerFactory {

    private final List<TaskHandler> handlers;

    private final Map<TaskType, TaskHandler> handlerMap =
            new EnumMap<>(TaskType.class);

    @PostConstruct
    public void init() {

        for (TaskHandler handler : handlers) {

            handlerMap.put(
                    handler.getTaskType(),
                    handler
            );
        }
    }

    public TaskHandler getHandler(
            TaskType taskType
    ) {

        return handlerMap.get(taskType);
    }
}