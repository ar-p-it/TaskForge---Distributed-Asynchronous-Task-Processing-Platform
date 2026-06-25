package com.example.asyncevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.asyncevents.entity.Task;

import java.util.UUID;
import com.example.asyncevents.enums.TaskStatus;
import java.util.List;
public interface TaskRepository
                extends JpaRepository<Task, UUID> {

        long countByStatus(TaskStatus pending);

        List<Task> findByStatus(TaskStatus status);
}
