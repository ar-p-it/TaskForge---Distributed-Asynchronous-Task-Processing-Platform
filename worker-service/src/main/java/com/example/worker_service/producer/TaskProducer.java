package com.example.worker_service.producer;

// import com.example.worker_service.event.TaskCreatedEvent;
import com.example.task_contracts.event.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTaskCreated(TaskCreatedEvent event) {

        kafkaTemplate.send(
                "task-created-topic",
                event.taskId().toString(),
                event);
    }
}