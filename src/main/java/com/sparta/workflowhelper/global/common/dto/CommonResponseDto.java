package com.sparta.workflowhelper.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CommonResponseDto<T> {

    private int statusCode;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> CommonResponseDto<T> of(int statusCode, String message, T data) {
        return CommonResponseDto.<T>builder()
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CommonResponseDto<T> of(int statusCode, String message) {
        return CommonResponseDto.<T>builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
