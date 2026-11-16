package com.likelion.modibe.domain.keyring.exception;

import com.likelion.modibe.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KeyringErrorCode implements ErrorCode {

    // 키링 조회
    KEYRING_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "키링을 찾을 수 없습니다."
    ),

    // 키링 등록
    REGISTRATION_CODE_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "등록 코드를 찾을 수 없습니다."
    ),

    KEYRING_ALREADY_REGISTERED(
            HttpStatus.CONFLICT,
            "이미 등록된 키링입니다."
    ),

    INVALID_KEYRING_STATUS(
            HttpStatus.CONFLICT,
            "현재 상태에서는 요청을 처리할 수 없습니다."
    ),

    // 키링 소유권
    KEYRING_ACCESS_DENIED(
            HttpStatus.FORBIDDEN,
            "본인 소유의 키링만 관리할 수 있습니다."
    ),

    // 키링 분실
    KEYRING_ALREADY_LOST(
            HttpStatus.CONFLICT,
            "이미 분실 처리된 키링입니다."
    ),

    KEYRING_NOT_REGISTERED(
            HttpStatus.CONFLICT,
            "등록된 키링만 분실 처리할 수 있습니다."
    ),

    // 키링 재발급
    ISSUANCE_REQUEST_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "키링 발급 신청 내역을 찾을 수 없습니다."
    ),

    ISSUANCE_ALREADY_REQUESTED(
            HttpStatus.CONFLICT,
            "이미 진행 중인 키링 발급 신청이 있습니다."
    ),

    INVALID_DELIVERY_ADDRESS(
            HttpStatus.BAD_REQUEST,
            "배송지 정보를 확인해주세요."
    ),

    KEYRING_LOST_PROFILE_NOT_ALLOWED(

            HttpStatus.CONFLICT,

            "분실 처리된 키링에는 접근성 프로필을 연결할 수 없습니다."

    ),

    KEYRING_ALREADY_CONNECTED(
            HttpStatus.CONFLICT,
        "이미 다른 접근성 프로필이 연결된 키링입니다."
    ),

    KEYRING_PROFILE_CONNECTION_NOT_ALLOWED(
            HttpStatus.CONFLICT,
        "등록된 키링에만 접근성 프로필을 연결할 수 있습니다."
    );
    private final HttpStatus status;
    private final String message;
}