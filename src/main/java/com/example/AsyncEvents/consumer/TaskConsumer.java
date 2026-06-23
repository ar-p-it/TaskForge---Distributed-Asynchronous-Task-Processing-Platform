package com.example.asyncevents.consumer;

import com.example.asyncevents.entity.Task;
import com.example.asyncevents.enums.TaskStatus;
import com.example.asyncevents.event.TaskCreatedEvent;
import com.example.asyncevents.producer.DlqProducer;
import com.example.asyncevents.producer.TaskProducer;
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
        private final TaskProducer taskProducer;

        // Day 7
        private final DlqProducer dlqProducer;

        @KafkaListener(topics = "task-created-topic", groupId = "task-group")
        public void consume(TaskCreatedEvent event) {

                log.info(
                                "Received task event: {}",
                                event.taskId());

                Task task = taskRepository
                                .findById(event.taskId())
                                .orElseThrow();

                try {

                        // PENDING -> PROCESSING
                        task.setStatus(TaskStatus.PROCESSING);
                        taskRepository.save(task);

                        log.info(
                                        "Task {} is PROCESSING",
                                        task.getId());

                        // // Simulate failure
                        // !This was for testing purposes of what if ur code base fails and how to
                        // handle it
                        throw new RuntimeException(
                                        "Simulated Failure");
                        // Thread.sleep(5000);

                        // task.setStatus(TaskStatus.COMPLETED);
                        // taskRepository.save(task);
                        // log.info(
                        // "Task {} COMPLETED",
                        // task.getId());

                } catch (Exception e) {

                        // task.setRetryCount(
                        // task.getRetryCount() + 1);

                        // task.setStatus(TaskStatus.FAILED);

                        // taskRepository.save(task);

                        // log.error(
                        // "Task {} failed",
                        // task.getId(),
                        // e);

                        task.setRetryCount(
                                        task.getRetryCount() + 1);

                        task.setStatus(TaskStatus.FAILED);

                        taskRepository.save(task);

                        if (task.getRetryCount() < 3) {
                                log.info(
                                                "Retrying task {}. Attempt {}",
                                                task.getId(),
                                                task.getRetryCount());

                                taskProducer.publishTaskCreated(
                                                new TaskCreatedEvent(
                                                                task.getId()));

                        } else {

                                dlqProducer.publishToDlq(
                                                new TaskCreatedEvent(
                                                                task.getId()));

                                log.error(
                                                "Task {} moved to DLQ",
                                                task.getId());
                        }
                        log.error(
                                        "Task {} failed",
                                        task.getId(),
                                        e);
                }
        }

}