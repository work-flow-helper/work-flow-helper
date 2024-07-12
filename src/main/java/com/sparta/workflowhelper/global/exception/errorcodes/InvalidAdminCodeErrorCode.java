package com.sparta.workflowhelper.global.exception.errorcodes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InvalidAdminCodeErrorCode {

    INVALID_ADMIN_CODE(HttpStatus.FORBIDDEN, "어드민 키가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
