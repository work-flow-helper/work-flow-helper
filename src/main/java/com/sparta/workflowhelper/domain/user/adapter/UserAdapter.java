package com.sparta.workflowhelper.domain.user.adapter;

import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.domain.user.repository.UserRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.UserNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_USER_ENTITY.getMessage()));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_USER_ENTITY.getMessage()));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
