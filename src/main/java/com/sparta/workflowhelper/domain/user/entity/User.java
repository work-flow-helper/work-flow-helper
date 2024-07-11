package com.sparta.workflowhelper.domain.user.entity;

import com.sparta.workflowhelper.domain.mapping.entity.UserBoardMapping;
import com.sparta.workflowhelper.global.common.entity.TimeStamped;
import com.sparta.workflowhelper.global.common.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_board_mapping_id")
    private UserBoardMapping userBoardMapping;

    @Builder
    public User(String username, String password, String nickname, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.userRole = userRole;
    }

    public static User createdUser(String username, String password, String nickname, String email, UserRole userRole) {
        return User.builder()
            .username(username)
            .password(password)
            .nickname(nickname)
            .email(email)
            .userRole(userRole)
            .build();
    }
}
