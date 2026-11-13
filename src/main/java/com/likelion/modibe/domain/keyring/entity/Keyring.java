package com.likelion.modibe.domain.keyring.entity;

import com.likelion.modibe.domain.profile.entity.Profile;
import com.likelion.modibe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
        name = "keyrings",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_keyrings_uid", columnNames = "uid"),
                @UniqueConstraint(
                        name = "uk_keyrings_registration_code",
                        columnNames = "registration_code"
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String uid;

    @Column(name = "registration_code", nullable = false, length = 6)
    private String registrationCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private KeyringStatus status = KeyringStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void register(User user) {
        if (this.status != KeyringStatus.AVAILABLE) {
            throw new IllegalStateException("이미 등록된 키링입니다.");
        }

        this.user = user;
        this.status = KeyringStatus.REGISTERED;
    }

    public void connectProfile(Profile profile) {
        if (this.status != KeyringStatus.REGISTERED) {
            throw new IllegalStateException("등록되지 않은 키링입니다.");
        }

        if (this.user == null ||
                !this.user.getId().equals(profile.getUser().getId())) {
            throw new IllegalArgumentException("키링과 프로필의 사용자가 다릅니다.");
        }

        this.profile = profile;
    }
}