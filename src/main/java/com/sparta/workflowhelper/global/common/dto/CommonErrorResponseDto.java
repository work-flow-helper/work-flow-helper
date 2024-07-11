package com.sparta.workflowhelper.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CommonErrorResponseDto {

    private int statusCode;

    private String message;

    public static CommonErrorResponseDto of(HttpStatus statusCode, String message) {
        return CommonErrorResponseDto.builder()
            .statusCode(statusCode.value())
            .message(message)
            .build();
    }
}