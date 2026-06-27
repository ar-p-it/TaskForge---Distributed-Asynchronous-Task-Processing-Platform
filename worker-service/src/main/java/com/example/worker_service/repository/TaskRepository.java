package com.example.worker_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.worker_service.entity.Task;

import java.util.UUID;
import com.example.worker_service.enums.TaskStatus;
import java.util.List;
public interface TaskRepository
                extends JpaRepository<Task, UUID> {

        long countByStatus(TaskStatus pending);

        List<Task> findByStatus(TaskStatus status);
}
