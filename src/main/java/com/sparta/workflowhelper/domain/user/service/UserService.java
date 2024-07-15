package com.sparta.workflowhelper.domain.user.service;

import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.dto.UpdateProfileRequestDto;
import com.sparta.workflowhelper.domain.user.dto.UserInfoResponseDto;
import com.sparta.workflowhelper.domain.user.dto.UserPagedResponseDto;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.exception.customexceptions.ProfileAccessException;
import com.sparta.workflowhelper.global.exception.errorcodes.ProfileAccessErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

    public UserPagedResponseDto<UserInfoResponseDto> getAllProfiles(Pageable pageable) {
        Page<User> profilePages = userAdapter.findAll(pageable);
        List<UserInfoResponseDto> userInfoResponseDtos = profilePages.getContent().stream()
            .map(profile -> UserInfoResponseDto.of(profile.getId(), profile.getNickname(), profile.getEmail()))
            .collect(Collectors.toList());

        return new UserPagedResponseDto<>(
            profilePages.getTotalPages(),
            profilePages.getTotalElements(),
            pageable.getPageSize(),
            pageable.getPageNumber(),
            userInfoResponseDtos
        );
    }

    public UserInfoResponseDto updateProfile(Long userId, UpdateProfileRequestDto requestDto, Long authenticatedUserId) {

        if (!userId.equals(authenticatedUserId)) {
            throw new ProfileAccessException(ProfileAccessErrorCode.PROFILE_ACCESS_ERROR_CODE.getMessage());
        }

        User user = userAdapter.findById(userId);
        user.updateProfile(requestDto.getNickname(), requestDto.getEmail());

        userAdapter.save(user);

        return UserInfoResponseDto.of(user.getId(), user.getNickname(), user.getEmail());
    }
}
