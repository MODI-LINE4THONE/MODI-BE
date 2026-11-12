package com.likelion.modibe.domain.keyring.entity;

public enum KeyringStatus {
    AVAILABLE,   // 등록 가능
    REGISTERED,  // 사용자 프로필에 등록여부
    LOST,        // 분실 여부
    DISABLED     // 사용 중지
}