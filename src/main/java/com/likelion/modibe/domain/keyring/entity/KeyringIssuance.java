package com.likelion.modibe.domain.keyring.entity;

import com.likelion.modibe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "keyring_issuances")
public class KeyringIssuance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신청 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 발급 수량
    @Column(nullable = false)
    private int quantity;

    // 우편번호
    @Column(nullable = false, length = 10)
    private String postalCode;

    // 기본 주소
    @Column(nullable = false)
    private String address;

    // 상세 주소
    @Column(nullable = false)
    private String detailAddress;

    // 발급 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IssuanceStatus status;

    // 신청 시간
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public KeyringIssuance(
            User user,
            int quantity,
            String postalCode,
            String address,
            String detailAddress
    ) {
        this.user = user;
        this.quantity = quantity;
        this.postalCode = postalCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.status = IssuanceStatus.REQUESTED;
        this.createdAt = LocalDateTime.now();
    }
}