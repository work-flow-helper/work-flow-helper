package com.sparta.workflowhelper.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserInfoResponseDto {

    private final Long userId;

    private final String nickname;

    private final String email;

    public static UserInfoResponseDto of(Long userId, String nickname, String email) {
        return UserInfoResponseDto.builder()
                .userId(userId)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
