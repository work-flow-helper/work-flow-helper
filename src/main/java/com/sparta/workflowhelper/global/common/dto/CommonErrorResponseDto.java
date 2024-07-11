package com.sparta.workflowhelper.global.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CommonErrorResponseDto<T> {

    private int statusCode;

    private T message;

    public static <T> CommonErrorResponseDto<T> of(int statusCode, T message) {
        return CommonErrorResponseDto.<T>builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}