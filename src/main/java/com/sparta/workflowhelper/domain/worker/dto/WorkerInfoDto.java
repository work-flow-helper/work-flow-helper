package com.sparta.workflowhelper.domain.worker.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class WorkerInfoDto {

    private Long workerId;

    private String workerName;

    public static WorkerInfoDto of(Long workerId, String workerName) {
        return WorkerInfoDto.builder()
                .workerId(workerId)
                .workerName(workerName)
                .build();
    }
}
