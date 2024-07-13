package com.sparta.workflowhelper.global.exception.errorcodes;

import lombok.Getter;

@Getter
public enum NotFoundErrorCode {

    NOT_FOUND_USER_ENTITY("유저를 찾을 수 없습니다."),

    NOT_FOUND_COMMENT_ENTITY("댓글을 찾을 수 없습니다."),

    NOT_FOUND_STAGE_ENTITY("스테이지를 찾을 수 없습니다."),

    NOT_FOUND_PROJECT_MEMBER_ENTITY("프로젝트에 초대된 유저가 아닙니다."),

    NOT_FOUND_CARD_ENTITY("카드를 찾을 수 없습니다."),

    NOT_FOUND_WORKER_ENTITY("작업자를 찾을 수 없습니다.");

    private final String message;

    NotFoundErrorCode(String message) {
        this.message = message;
    }
}
