package com.sparta.workflowhelper.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProfileRequestDto {
    @NotBlank(message = "수정할 닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "수정할 이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;
}
