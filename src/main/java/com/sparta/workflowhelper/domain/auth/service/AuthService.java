package com.sparta.workflowhelper.domain.auth.service;

import com.sparta.workflowhelper.domain.auth.adapter.AuthAdapter;
import com.sparta.workflowhelper.domain.auth.dto.AuthRequestDto;
import com.sparta.workflowhelper.domain.auth.dto.AuthResponseDto;
import com.sparta.workflowhelper.domain.auth.dto.LoginRequestDto;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.common.enums.UserRole;
import com.sparta.workflowhelper.global.common.enums.UserStatus;
import com.sparta.workflowhelper.global.exception.customexceptions.InvalidAdminCodeException;
import com.sparta.workflowhelper.global.exception.customexceptions.TokenInvalidException;
import com.sparta.workflowhelper.global.exception.customexceptions.UserDuplicateException;
import com.sparta.workflowhelper.global.exception.errorcodes.InvalidAdminCodeErrorCode;
import com.sparta.workflowhelper.global.exception.errorcodes.UserDuplicateErrorCode;
import com.sparta.workflowhelper.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthAdapter authAdapter;
    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    // 환경 변수 불러오기
    @Value("${admin.code}")
    private String adminCode;

    public AuthResponseDto signUp(AuthRequestDto requestDto) {
        // 중복된 username 확인
        if (authAdapter.existsByUsername(requestDto.getUsername())) {
            throw new UserDuplicateException(UserDuplicateErrorCode.DUPLICATE_USERNAME.getMessage());
        }

        UserStatus status = UserStatus.ACTIVE;
        UserRole role = UserRole.USER;

        // manager 권한을 위한 ADMIN KEY 확인
        if (requestDto.getAdminCode() != null) {
            if (adminCode.equals(requestDto.getAdminCode())) {
                role = UserRole.MANAGER;
            } else {
                throw new InvalidAdminCodeException(InvalidAdminCodeErrorCode.INVALID_ADMIN_CODE.getMessage());  // Forbidden error
            }
        }

        User user = User.createdUser(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), requestDto.getNickname(), requestDto.getEmail(), status, role);

        User savedUser = authAdapter.save(user);

        return AuthResponseDto.of(savedUser.getId(), savedUser.getNickname(), savedUser.getEmail());
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse response) {

        User user = authAdapter.findByUsername(requestDto.getUsername());

        user.checkUserWithdrawn();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserRole role = user.getUserRole();

        String jwtAccessToken = jwtProvider.createdAccessToken(requestDto.getUsername(), role);

        String jwtRefreshToken = jwtProvider.createdRefreshToken(requestDto.getUsername());

        user.updateRefreshToken(jwtRefreshToken);
        authAdapter.save(user);

        response.setHeader(JwtProvider.ACCESS_HEADER, jwtAccessToken);
        response.setHeader(JwtProvider.REFRESH_HEADER, jwtRefreshToken);

    }

    public void withdraw(HttpServletRequest request) {

        String withdrawToken = jwtProvider.getJwtFromHeader(request, "Authorization");

        jwtProvider.checkJwtToken(withdrawToken);

        String username = jwtProvider.getUserNameFromJwtToken(withdrawToken);

        User user = userAdapter.findByUsername(username);

        user.checkUserWithdrawn();

        user.updateStatus(UserStatus.WITHDRAWN);

        authAdapter.save(user);
        SecurityContextHolder.clearContext();

    }

    public void logout(HttpServletRequest request) {

        String logoutToken = jwtProvider.getJwtFromHeader(request, "Authorization");

        jwtProvider.checkJwtToken(logoutToken);

        String username = jwtProvider.getUserNameFromJwtToken(logoutToken);

        User user = authAdapter.findByUsername(username);

        user.updateRefreshToken(null);
        authAdapter.save(user);
        SecurityContextHolder.clearContext();

    }

    public void tokenReissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = jwtProvider.getJwtFromHeader(request, JwtProvider.REFRESH_HEADER);

        jwtProvider.checkJwtToken(refreshToken);

        String refreshUsername = jwtProvider.getUserNameFromJwtToken(refreshToken);

        User user = authAdapter.findByUsername(refreshUsername);

        String storedRefreshToken = user.getRefreshToken().replace("Bearer ", "");

        if (!storedRefreshToken.equals(refreshToken)) {
            throw new TokenInvalidException("토큰 정보가 일치하지 않습니다.");
        }

        issueTokenAndSave(response, user);
    }

    @Transactional
    public void issueTokenAndSave(HttpServletResponse response, User user) {
        String newAccessToken = jwtProvider.createdAccessToken(user.getUsername(), user.getUserRole());
        String newRefreshToken = jwtProvider.createdRefreshToken(user.getUsername());

        user.updateRefreshToken(newRefreshToken);

        response.setHeader(JwtProvider.ACCESS_HEADER, newAccessToken);
        response.setHeader(JwtProvider.REFRESH_HEADER, newRefreshToken);
    }
}

