package com.example.worker_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void sendNotification(String payload) {

        log.info(
                "Sending notification: {}",
                payload);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(
                "Notification sent: {}",
                payload);

    }
}