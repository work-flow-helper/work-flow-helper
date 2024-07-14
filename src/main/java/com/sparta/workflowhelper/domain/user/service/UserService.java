package com.sparta.workflowhelper.domain.user.service;

import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.dto.UpdateProfileRequestDto;
import com.sparta.workflowhelper.domain.user.dto.UserInfoResponseDto;
import com.sparta.workflowhelper.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;

    public UserInfoResponseDto getProfile(Long userId) {

       User user = userAdapter.findById(userId);

        return UserInfoResponseDto.of(user.getId(), user.getNickname(),user.getEmail());
    }

    public List<UserInfoResponseDto> getAllProfiles() {
        return null;
    }

    public UserInfoResponseDto updateProfile(Long userId, UpdateProfileRequestDto requestDto) {

        User user = userAdapter.findById(userId);
        user.updateProfile(requestDto.getNickname(), requestDto.getEmail());

        userAdapter.save(user);

        return UserInfoResponseDto.of(user.getId(), user.getNickname(), user.getEmail());
    }
}
