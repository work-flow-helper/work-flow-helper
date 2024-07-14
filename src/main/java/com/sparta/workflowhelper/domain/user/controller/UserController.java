package com.sparta.workflowhelper.domain.user.controller;

import com.sparta.workflowhelper.domain.user.dto.UpdateProfileRequestDto;
import com.sparta.workflowhelper.domain.user.dto.UserInfoResponseDto;
import com.sparta.workflowhelper.domain.user.service.UserService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CommonResponseDto<List<UserInfoResponseDto>>> getAllProfile() {

        List<UserInfoResponseDto> responseDto = userService.getAllProfiles();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "유저 전체 조회 성공", responseDto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponseDto<UserInfoResponseDto>> updateProfile(@PathVariable Long userId,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                @Valid @RequestBody UpdateProfileRequestDto requestDto) {

        if (!userId.equals(userDetails.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(CommonResponseDto.of(HttpStatus.FORBIDDEN.value(), "본인의 프로필만 수정할 수 있습니다."));
        }

        UserInfoResponseDto responseDto = userService.updateProfile(userId, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseDto.of(HttpStatus.OK.value(), "유저 프로필 수정", responseDto));
    }
}
