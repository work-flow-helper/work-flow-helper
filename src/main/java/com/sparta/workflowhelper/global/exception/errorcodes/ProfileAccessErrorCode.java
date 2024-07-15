package com.sparta.workflowhelper.global.exception.errorcodes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProfileAccessErrorCode {

    PROFILE_ACCESS_ERROR_CODE(HttpStatus.FORBIDDEN, "본인의 프로필만 수정할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
