package com.example.asyncevents.event;


import java.util.UUID;

public record TaskCreatedEvent(
        UUID taskId
) {
}