package com.sparta.workflowhelper.domain.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkQueryDto {

    private Long workerId;

    private String workerName;
}
