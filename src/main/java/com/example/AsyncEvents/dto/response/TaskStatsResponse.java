package com.example.asyncevents.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskStatsResponse {

    private long pending;
    private long processing;
    private long completed;
    private long failed;
}