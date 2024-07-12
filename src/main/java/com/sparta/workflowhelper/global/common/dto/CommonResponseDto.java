package com.sparta.workflowhelper.global.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
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
