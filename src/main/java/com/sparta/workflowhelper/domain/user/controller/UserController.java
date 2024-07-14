package com.sparta.workflowhelper.domain.user.controller;

import com.sparta.workflowhelper.domain.user.dto.UserInfoResponseDto;
import com.sparta.workflowhelper.domain.user.service.UserService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponseDto<UserInfoResponseDto>> getProfile(@PathVariable Long userId,
          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserInfoResponseDto responseDto = userService.getProfile(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "유저 단일 조회 성공", responseDto));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<List<UserInfoResponseDto>>> getAllProfile() {

        List<UserInfoResponseDto> responseDto = userService.getAllProfiles();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "유저 전체 조회 성공", responseDto));
    }
}
