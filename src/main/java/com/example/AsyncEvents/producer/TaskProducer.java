package com.example.asyncevents.producer;

// import com.example.asyncevents.event.TaskCreatedEvent;
import com.example.task_contracts.event.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTaskCreated(TaskCreatedEvent event) {

kafkaTemplate.send(
        "task-created-topic",
        event.taskId().toString(),
        event)
.whenComplete((result, ex) -> {
    if (ex == null) {
        log.info("Task {} successfully published to Kafka", event.taskId());
    } else {
        log.error("Failed to publish task {}", event.taskId(), ex);
    }
});
          

    }
}