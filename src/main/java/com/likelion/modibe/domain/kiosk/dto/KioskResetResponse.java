package com.likelion.modibe.domain.kiosk.dto;

public record KioskResetResponse(
        String kioskCode,
        String status,
        String message
) {
    public static KioskResetResponse of(String kioskCode) {
        return new KioskResetResponse(
                kioskCode,
                "RESET_REQUESTED",
                "키오스크 접근성 설정 초기화 요청이 완료되었습니다."
        );
    }
}