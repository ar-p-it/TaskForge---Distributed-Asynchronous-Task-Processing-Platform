package com.example.asyncevents.producer;

import com.example.asyncevents.event.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DlqProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishToDlq(
            TaskCreatedEvent event
    ) {

        kafkaTemplate.send(
                "task-dlq-topic",
                event.taskId().toString(),
                event
        );
    }
}