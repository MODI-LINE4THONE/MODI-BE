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

    @Column(nullable = false)
    private boolean largeText;

    @Column(nullable = false)
    private boolean highContrast;

    @Column(nullable = false)
    private boolean simplifiedMenu;

    @Column(nullable = false)
    private boolean lowerScreen;

    @Column(nullable = false)
    private boolean touchGuide;


    public Profile(
            User user,
            String name,
            boolean largeText,
            boolean highContrast,
            boolean simplifiedMenu,
            boolean lowerScreen,
            boolean touchGuide
    ) {
        this.user = user;
        this.name = name;
        this.largeText = largeText;
        this.highContrast = highContrast;
        this.simplifiedMenu = simplifiedMenu;
        this.lowerScreen = lowerScreen;
        this.touchGuide = touchGuide;
    }
}