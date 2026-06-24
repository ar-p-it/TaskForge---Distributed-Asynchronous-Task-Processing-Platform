package com.example.asyncevents.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void sendNotification(String payload) {

        log.info(
                "Sending notification: {}",
                payload
        );
    }
}