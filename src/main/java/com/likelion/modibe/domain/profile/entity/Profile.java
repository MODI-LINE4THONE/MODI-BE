package com.likelion.modibe.domain.profile.entity;

import com.likelion.modibe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String name;

    // 글자 크게
    @Column(nullable = false)
    private boolean largeText;

    // 색상 고대비
    @Column(nullable = false)
    private boolean highContrast;

    // 터치 위치 안내
    @Column(nullable = false)
    private boolean touchGuide;

    // 화면 하단 조작
    @Column(nullable = false)
    private boolean lowerScreen;

    // 터치 입력 보정
    @Column(nullable = false)
    private boolean touchCorrection;

    public Profile(
            User user,
            String name,
            boolean largeText,
            boolean highContrast,
            boolean touchGuide,
            boolean lowerScreen,
            boolean touchCorrection
    ) {
        this.user = user;
        this.name = name;
        this.largeText = largeText;
        this.highContrast = highContrast;
        this.touchGuide = touchGuide;
        this.lowerScreen = lowerScreen;
        this.touchCorrection = touchCorrection;
    }

    public void update(
            String name,
            Boolean largeText,
            Boolean highContrast,
            Boolean touchGuide,
            Boolean lowerScreen,
            Boolean touchCorrection
    ) {
        if (name != null) {
            this.name = name;
        }

        if (largeText != null) {
            this.largeText = largeText;
        }

        if (highContrast != null) {
            this.highContrast = highContrast;
        }

        if (touchGuide != null) {
            this.touchGuide = touchGuide;
        }

        if (lowerScreen != null) {
            this.lowerScreen = lowerScreen;
        }

        if (touchCorrection != null) {
            this.touchCorrection = touchCorrection;
        }
    }
}