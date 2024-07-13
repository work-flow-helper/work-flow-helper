package com.sparta.workflowhelper.global.exception.errorcodes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AlreadyWithdrawnErrorCode {

    ALREADY_WITHDRAWN(HttpStatus.CONFLICT, "이미 탈퇴한 회원입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
