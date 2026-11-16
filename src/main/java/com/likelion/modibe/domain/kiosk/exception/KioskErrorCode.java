package com.likelion.modibe.domain.kiosk.exception;

import com.likelion.modibe.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KioskErrorCode implements ErrorCode {

    // 키오스크 조회
    KIOSK_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "존재하지 않는 키오스크입니다."
    ),

    // NFC 인식
    NFC_KEYRING_NOT_REGISTERED(
            HttpStatus.CONFLICT,
            "등록되지 않은 키링은 사용할 수 없습니다."
    ),

    NFC_KEYRING_UNAVAILABLE(
            HttpStatus.CONFLICT,
            "현재 사용할 수 없는 키링입니다."
    ),

    // 접근성 프로필
    PROFILE_NOT_CONNECTED(
            HttpStatus.CONFLICT,
            "키링에 연결된 접근성 프로필이 없습니다."
    );

    private final HttpStatus status;
    private final String message;
}