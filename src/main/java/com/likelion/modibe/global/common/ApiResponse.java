package com.likelion.modibe.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "공통 API 응답")
public class ApiResponse<T> {

    @Schema(
            description = "요청 성공 여부",
            example = "true"
    )
    private final boolean success;

    @Schema(
            description = "응답 메시지",
            example = "요청이 성공적으로 처리되었습니다."
    )
    private final String message;

    @Schema(
            description = "응답 데이터 (API별 Response DTO)",
            nullable = true
    )
    private final T data;

    // 데이터가 있는 성공 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // 데이터가 없는 성공 응답
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}