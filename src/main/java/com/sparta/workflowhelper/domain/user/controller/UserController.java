package com.sparta.workflowhelper.domain.user.controller;

import com.sparta.workflowhelper.domain.user.dto.UpdateProfileRequestDto;
import com.sparta.workflowhelper.domain.user.dto.UserInfoResponseDto;
import com.sparta.workflowhelper.domain.user.dto.UserPagedResponseDto;
import com.sparta.workflowhelper.domain.user.service.UserService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponseDto<UserInfoResponseDto>> getProfile(@PathVariable Long userId) {

        UserInfoResponseDto responseDto = userService.getProfile(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "유저 단일 조회 성공", responseDto));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<UserPagedResponseDto<UserInfoResponseDto>>> getAllProfile(Pageable pageable) {

        UserPagedResponseDto<UserInfoResponseDto> responseDto = userService.getAllProfiles(pageable);

        return ResponseEntity.ok(CommonResponseDto.of(HttpStatus.OK.value(), "유저 전체 조회 성공", responseDto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponseDto<UserInfoResponseDto>> updateProfile(@PathVariable Long userId,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                @Valid @RequestBody UpdateProfileRequestDto requestDto) {

        UserInfoResponseDto responseDto = userService.updateProfile(userId, requestDto, userDetails.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "유저 프로필 수정", responseDto));
    }
}
