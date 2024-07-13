package com.sparta.workflowhelper.domain.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AuthRequestDto {

    @Pattern(regexp = "^[a-z0-9]+$", message = "소문자 영문(a~z)과 숫자(0~9)만 허용됩니다.")
    @Size(min = 4, max = 10, message = "사용자 ID는 최소 4글자 이상, 최대 10글자 이하여야 합니다.")
    @NotBlank(message = "username 은 공백일 수 없습니다.")
    // username: 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,15}$", message = "비밀번호는 대소문자 영문(a~z, A~Z) + 숫자(0~9) + 특수문자를 최소 1글자씩 포함해야 합니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8글자 이상, 최대 15글자 이하여야 합니다.")
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    // password: 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자
    private String password;

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    private String nickname;

    @Email(message = "이메일 형식만 가능합니다.")
    private String email;

    private String adminCode;

}
