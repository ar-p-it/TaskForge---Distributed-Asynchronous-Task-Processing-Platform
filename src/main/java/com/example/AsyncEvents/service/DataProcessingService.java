package com.example.asyncevents.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataProcessingService {

    public void processData(String payload) {

        log.info(
                "Processing data: {}",
                payload
        );
    }
}