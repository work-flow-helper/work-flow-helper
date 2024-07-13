package com.sparta.workflowhelper.domain.auth.adapter;

import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.domain.user.repository.UserRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.UserNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthAdapter {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(NotFoundErrorCode.NOT_FOUND_USER_ENTITY.getMessage()));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
