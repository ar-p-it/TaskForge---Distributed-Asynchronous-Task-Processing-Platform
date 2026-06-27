package com.example.task_contracts.event;

import java.util.UUID;

public record TaskCreatedEvent(
        UUID taskId) {
}