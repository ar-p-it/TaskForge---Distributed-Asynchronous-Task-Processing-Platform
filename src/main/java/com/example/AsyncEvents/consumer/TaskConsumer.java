package com.example.asyncevents.consumer;

import com.example.asyncevents.event.TaskCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskConsumer {

    @KafkaListener(
            topics = "task-created-topic",
            groupId = "task-group"
    )
    public void consume(TaskCreatedEvent event) {

        log.info(
                "Received task event: {}",
                event.taskId()
        );
    }
}


