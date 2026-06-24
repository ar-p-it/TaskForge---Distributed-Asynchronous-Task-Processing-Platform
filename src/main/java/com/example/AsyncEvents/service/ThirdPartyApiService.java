package com.example.asyncevents.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThirdPartyApiService {

    public void callApi(String payload) {

        log.info(
                "Calling external API with payload: {}",
                payload
        );
    }
}