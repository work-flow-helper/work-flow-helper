package com.sparta.workflowhelper.global.common.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE("ACTIVE"),
    WITHDRAWN("WITHDRAWN");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
