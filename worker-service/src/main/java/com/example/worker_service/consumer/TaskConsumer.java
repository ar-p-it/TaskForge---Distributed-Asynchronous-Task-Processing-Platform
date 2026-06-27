package com.example.worker_service.consumer;

import com.example.worker_service.entity.Task;
import com.example.worker_service.enums.TaskStatus;
// import com.example.worker_service.event.TaskCreatedEvent;
import com.example.task_contracts.event.TaskCreatedEvent;
import com.example.worker_service.handler.TaskHandler;
import com.example.worker_service.handler.TaskHandlerFactory;
import com.example.worker_service.producer.DlqProducer;
import com.example.worker_service.producer.TaskProducer;
import com.example.worker_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.task_contracts.enums.TaskType;
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskConsumer {

        private final TaskRepository taskRepository;
        private final TaskProducer taskProducer;

        // Day 7
        private final DlqProducer dlqProducer;

        private final TaskHandlerFactory taskHandlerFactory;

        @KafkaListener(topics = "task-created-topic", groupId = "task-group")
        public void consume(TaskCreatedEvent event) {

                // log.info(
                // "Received task event: {}",
                // event.taskId());
                log.info(
                                "Consumer [{}] received task {}",
                                Thread.currentThread().getName(),
                                event.taskId());
                Task task = taskRepository
                                .findById(event.taskId())
                                .orElse(null);

                if (task == null) {

                        // log.error(
                        // "Task {} not found",
                        // event.taskId());
                        log.error(
                                        "Task {} not found",
                                        event.taskId());

                        return;

                }

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
                        // throw new RuntimeException(
                        // "Simulated Failure");

                        TaskHandler handler = taskHandlerFactory.getHandler(
                                        task.getType());

                        handler.process(task);
                        // Thread.sleep(5000);

                        task.setStatus(TaskStatus.COMPLETED);
                        taskRepository.save(task);
                        log.info(
                                        "Task {} COMPLETED",
                                        task.getId());

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
                                        "Task {} failed: {}",
                                        task.getId(),
                                        e.getMessage());
                }
        }

}