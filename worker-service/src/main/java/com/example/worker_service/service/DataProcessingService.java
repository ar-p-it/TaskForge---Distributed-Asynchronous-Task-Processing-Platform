package com.example.worker_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataProcessingService {

    public void processData(String payload) {

        log.info(
                "Processing data: {}",
                payload);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info(
                "Data processing completed: {}",
                payload);
    }
}