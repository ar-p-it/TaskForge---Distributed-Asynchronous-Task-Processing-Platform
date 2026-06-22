package com.example.asyncevents.consumer;

import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskStatus;
import com.example.asyncevents.event.TaskCreatedEvent;
import com.example.asyncevents.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskRepository taskRepository;

    @KafkaListener(
            topics = "task-created-topic",
            groupId = "task-group"
    )
    public void consume(TaskCreatedEvent event)
            throws InterruptedException {

        log.info(
                "Received task event: {}",
                event.taskId()
        );

        Task task = taskRepository
                .findById(event.taskId())
                .orElseThrow();

        // PENDING -> PROCESSING
        task.setStatus(TaskStatus.PROCESSING);
        taskRepository.save(task);

        log.info(
                "Task {} is PROCESSING",
                task.getId()
        );

        // Simulate work
        Thread.sleep(5000);

        // PROCESSING -> COMPLETED
        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);

        log.info(
                "Task {} COMPLETED",
                task.getId()
        );
    }
}