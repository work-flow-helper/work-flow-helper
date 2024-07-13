package com.sparta.workflowhelper.global.exception.errorcodes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserDuplicateErrorCode {
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 사용자 명이 존재합니다.");

    private final HttpStatus status;
    private final String message;
}
