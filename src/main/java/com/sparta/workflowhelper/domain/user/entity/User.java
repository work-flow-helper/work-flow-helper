package com.sparta.workflowhelper.domain.user.entity;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.global.common.entity.TimeStamped;
import com.sparta.workflowhelper.global.common.enums.UserRole;
import com.sparta.workflowhelper.global.common.enums.UserStatus;
import com.sparta.workflowhelper.global.exception.customexceptions.AlreadyWithdrawnException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true, nullable = false)
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

    @Column
    private String refreshToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private User(String username, String password, String nickname, String email, UserStatus userStatus,
                 UserRole userRole) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    public static User createdUser(String username, String password, String nickname, String email, UserStatus userStatus,
                                   UserRole userRole) {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .userStatus(userStatus)
                .userRole(userRole)
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateStatus(UserStatus status) {
        this.userStatus = status;
    }

    public void updateProfile(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public void checkUserWithdrawn() {
        if (this.userStatus == UserStatus.WITHDRAWN) {
            throw new AlreadyWithdrawnException("이미 탈퇴한 회원입니다.");
        }

    }
}
