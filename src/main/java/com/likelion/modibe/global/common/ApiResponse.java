package com.likelion.modibe.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // 데이터가 있는 성공 응답
    public static <T> ApiResponse<T> success(
            String message, T data) {

        return new ApiResponse<>(
                true,
                message,
                data
        );
    }

    // 데이터가 없는 성공 응답
    public static ApiResponse<Void> success(
            String message) {

        return new ApiResponse<>(
                true,
                message,
                null
        );
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(
            String message) {

        return new ApiResponse<>(
                false,
                message,
                null
        );
    }
}