package com.sparta.workflowhelper.domain.auth.controller;

import com.sparta.workflowhelper.domain.auth.dto.AuthRequestDto;
import com.sparta.workflowhelper.domain.auth.dto.AuthResponseDto;
import com.sparta.workflowhelper.domain.auth.dto.LoginRequestDto;
import com.sparta.workflowhelper.domain.auth.service.AuthService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원 가입
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponseDto<AuthResponseDto>> signUp(@Valid @RequestBody AuthRequestDto requestDto) {

        AuthResponseDto responseDto = authService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.of(HttpStatus.CREATED.value(), "회원가입 성공", responseDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<String>> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {

        authService.login(requestDto, response);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "로그인 성공"));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<CommonResponseDto<String>> withdraw(HttpServletRequest request) {

        authService.withdraw(request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(CommonResponseDto.of(HttpStatus.NO_CONTENT.value(), "회원탈퇴 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto<String>> logout(HttpServletRequest request) {

        authService.logout(request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(CommonResponseDto.of(HttpStatus.NO_CONTENT.value(), "로그아웃 성공"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto<String>> tokenReissue(HttpServletRequest request, HttpServletResponse response) {

        authService.tokenReissue(request, response);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "토큰 재발행 성공"));
    }
}
