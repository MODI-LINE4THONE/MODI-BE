
package com.likelion.modibe.domain.user.controller;

import com.likelion.modibe.domain.user.dto.OnboardingRequest;
import com.likelion.modibe.domain.user.dto.OnboardingResponse;
import com.likelion.modibe.domain.user.dto.UserResponse;
import com.likelion.modibe.domain.user.service.UserService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 온보딩 프로필 생성
    @PostMapping("/me/onboarding")
    public ResponseEntity<ApiResponse<OnboardingResponse>> startOnboarding(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody OnboardingRequest request
    ) {
        OnboardingResponse response =
                userService.startOnboarding(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "온보딩 프로필 생성 성공",
                        response
                ));
    }

    // 온보딩 완료
    @PatchMapping("/me/onboarding/complete")
    public ResponseEntity<ApiResponse<UserResponse>> completeOnboarding(
            @AuthenticationPrincipal Long userId
    ) {
        UserResponse response =
                userService.completeOnboarding(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "온보딩 완료",
                        response
                )
        );
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(
            @AuthenticationPrincipal Long userId
    ) {
        UserResponse response =
                userService.getMyInfo(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "내 정보 조회 성공",
                        response
                )
        );
    }
}
