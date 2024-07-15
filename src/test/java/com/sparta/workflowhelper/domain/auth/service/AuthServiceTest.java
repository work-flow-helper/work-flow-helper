package com.sparta.workflowhelper.domain.auth.service;

import com.sparta.workflowhelper.domain.auth.adapter.AuthAdapter;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.common.enums.UserRole;
import com.sparta.workflowhelper.global.common.enums.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthAdapter authAdapter;

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Test
    @DisplayName("init User data")
    public void initUserData() {

        long userNumber = 0;

        for (int i = 0; i < 888888; i++) {
            User user = User.createdUser(
                    "abc" + userNumber,
                    "password",
                    "nick" + userNumber,
                    "em" + userNumber + "@email.com",
                    UserStatus.ACTIVE,
                    UserRole.USER
            );

            userAdapter.save(user);

            userNumber++;
        }
    }
}