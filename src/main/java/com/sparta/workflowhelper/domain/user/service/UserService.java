package com.sparta.workflowhelper.domain.user.service;

import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.dto.UserInfoResponseDto;
import com.sparta.workflowhelper.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;

    public UserInfoResponseDto getProfile(Long userId) {

       User user = userAdapter.findById(userId);

        return UserInfoResponseDto.of(user.getId(), user.getNickname(),user.getEmail());
    }
}
