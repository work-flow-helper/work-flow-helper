package com.sparta.workflowhelper.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class AuthResponseDto {

    private final Long userId;

    private final String nickname;

    private final String email;

    public static AuthResponseDto of(Long userId, String nickname, String email) {
        return AuthResponseDto.builder()
                .userId(userId)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
