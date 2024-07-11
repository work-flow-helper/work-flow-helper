package com.sparta.workflowhelper.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponseDto<T> {

    private int statusCode;

    private String message;

    private T data;

    public static <T> CommonResponseDto<T> of(int statusCode, String message, T data) {
        return CommonResponseDto.<T>builder()
            .statusCode(statusCode)
            .message(message)
            .data(data)
            .build();
    }
}
