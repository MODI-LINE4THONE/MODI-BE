package com.likelion.modibe.domain.keyring.entity;

public enum IssuanceStatus {
    REQUESTED,  // 신청 완료
    APPROVED,   // 신청 승인
    SHIPPING,   // 배송 중
    DELIVERED,  // 배송 완료
    CANCELLED   // 신청 취소
}