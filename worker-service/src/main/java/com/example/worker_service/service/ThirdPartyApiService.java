package com.example.worker_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThirdPartyApiService {

    public void callApi(String payload) {

        log.info(
                "Calling external API with payload: {}",
                payload);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (payload.equalsIgnoreCase("fail")) {

            throw new RuntimeException(
                    "Third Party API Failed");
        }

        log.info(
                "Third Party API Success");
    }
}