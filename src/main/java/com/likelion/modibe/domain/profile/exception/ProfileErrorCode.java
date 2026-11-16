package com.likelion.modibe.domain.profile.exception;

import com.likelion.modibe.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {

    PROFILE_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "접근성 프로필을 찾을 수 없습니다."
    ),

    PROFILE_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "이미 접근성 프로필이 연결된 키링입니다."
    ),

    PROFILE_ACCESS_DENIED(
            HttpStatus.FORBIDDEN,
            "본인의 접근성 프로필만 관리할 수 있습니다."
    ),

    PROFILE_NAME_DUPLICATED( HttpStatus.CONFLICT,
            "중복된 이름의 프로필은 생성할 수 없습니다."
    );

    private final HttpStatus status;
    private final String message;
}