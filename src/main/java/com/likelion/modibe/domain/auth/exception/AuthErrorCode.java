
package com.likelion.modibe.domain.auth.exception;

import com.likelion.modibe.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    INVALID_CREDENTIALS(
            HttpStatus.UNAUTHORIZED,
            "아이디 또는 비밀번호가 올바르지 않습니다."
    ),

    UNAUTHORIZED(
            HttpStatus.UNAUTHORIZED,
            "인증이 필요합니다."
    ),

    ACCESS_DENIED(
            HttpStatus.FORBIDDEN,
            "접근 권한이 없습니다."
    ),

    INVALID_REFRESH_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "유효하지 않은 리프레시 토큰입니다."
    );

    private final HttpStatus status;
    private final String message;
}
