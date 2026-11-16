package com.likelion.modibe.domain.kiosk.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "kiosks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Kiosk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 키오스크 고유 식별자
    @Column(nullable = false, unique = true, length = 50)
    private String kioskCode;

    // 키오스크 이름
    @Column(nullable = false, length = 100)
    private String name;

    // 키오스크 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private KioskStatus status;

    public Kiosk(String kioskCode, String name) {
        this.kioskCode = kioskCode;
        this.name = name;
        this.status = KioskStatus.AVAILABLE;
    }



    public void disconnect() {
        this.status = KioskStatus.OFFLINE;
    }
}