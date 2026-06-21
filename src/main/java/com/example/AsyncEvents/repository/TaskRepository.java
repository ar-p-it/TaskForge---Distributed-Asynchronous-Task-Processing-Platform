package com.example.AsyncEvents.repository;

import com.example.AsyncEvents.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository
        extends JpaRepository<Task, UUID> {
}