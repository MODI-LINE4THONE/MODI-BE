package com.likelion.modibe.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_login_id",
                        columnNames = "login_id"
                )
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 20)
    private UserType userType;

    @Column(
            name = "onboarding_completed",
            nullable = false
    )
    private boolean onboardingCompleted = false;

    public User(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public void selectUserType(UserType userType) {
        if (userType == null) {
            throw new IllegalArgumentException(
                    "사용자 유형은 필수입니다."
            );
        }

        if (this.onboardingCompleted) {
            throw new IllegalStateException(
                    "이미 온보딩을 완료한 사용자입니다."
            );
        }

        this.userType = userType;
    }

    public void completeOnboarding() {
        if (this.userType == null) {
            throw new IllegalStateException(
                    "사용자 유형을 먼저 선택해야 합니다."
            );
        }

        if (this.onboardingCompleted) {
            throw new IllegalStateException(
                    "이미 온보딩을 완료했습니다."
            );
        }

        this.onboardingCompleted = true;
    }
}