package com.sparta.workflowhelper.global.exception.errorcodes;

import lombok.Getter;

@Getter
public enum TokenErrorCode {
    TOKEN_EXPIRED("Token Expired"),
    TOKEN_INVALID("Token Invalid");

    private final String message;

    TokenErrorCode(String message) {
        this.message = message;
    }
}
