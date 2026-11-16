package com.likelion.modibe.domain.user.controller;

import com.likelion.modibe.domain.user.dto.OnboardingRequest;
import com.likelion.modibe.domain.user.dto.OnboardingResponse;
import com.likelion.modibe.domain.user.dto.UserResponse;
import com.likelion.modibe.domain.user.service.UserService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "사용자 관리",
        description = "사용자 온보딩 및 내 정보 조회 API"
)
public class UserController {

    private final UserService userService;

    // 온보딩 프로필 생성
    @Operation(
            summary = "온보딩 프로필 생성",
            description = """
                    로그인한 사용자의 초기 접근성 프로필을 생성합니다.

                    - 온보딩에 필요한 접근성 설정 정보를 입력합니다.
                    - 입력한 정보를 기반으로 초기 프로필을 생성합니다.
                    - 생성 성공 시 201 Created를 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PostMapping("/me/onboarding")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<OnboardingResponse>> startOnboarding(
            @Parameter(hidden = true)
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
    @Operation(
            summary = "온보딩 완료",
            description = """
                    로그인한 사용자의 온보딩을 완료 처리합니다.

                    - 사용자의 온보딩 완료 상태를 반영합니다.
                    - 완료 처리 후 사용자 정보를 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PatchMapping("/me/onboarding/complete")
    public ResponseEntity<ApiResponse<UserResponse>> completeOnboarding(
            @Parameter(hidden = true)
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
    @Operation(
            summary = "내 정보 조회",
            description = """
                    로그인한 사용자의 정보를 조회합니다.

                    - JWT 인증 정보를 기반으로 현재 사용자를 식별합니다.
                    - 조회한 사용자 정보를 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(
            @Parameter(hidden = true)
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