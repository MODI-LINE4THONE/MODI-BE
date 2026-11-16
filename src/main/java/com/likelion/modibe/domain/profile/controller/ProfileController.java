package com.likelion.modibe.domain.profile.controller;

import com.likelion.modibe.domain.keyring.dto.KeyringResponse;
import com.likelion.modibe.domain.profile.dto.ProfileCreateRequest;
import com.likelion.modibe.domain.profile.dto.ProfileResponse;
import com.likelion.modibe.domain.profile.dto.ProfileUpdateRequest;
import com.likelion.modibe.domain.profile.service.ProfileService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "접근성 프로필 관리",
        description = "접근성 프로필 생성, 수정, 조회 및 연결된 NFC 키링 조회 API"
)
public class ProfileController {

    private final ProfileService profileService;

    // 접근성 프로필 생성
    @Operation(
            summary = "접근성 프로필 생성",
            description = """
                    로그인한 사용자의 새로운 접근성 프로필을 생성합니다.

                    - 접근성 설정 정보를 입력하여 프로필을 생성합니다.
                    - 생성된 프로필의 ID를 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Map<String, Long>>> createProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Valid @RequestBody ProfileCreateRequest request
    ) {
        Long profileId = profileService.createProfile(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "프로필 생성 성공",
                        Map.of("profileId", profileId)
                ));
    }

    // 접근성 프로필 수정
    @Operation(
            summary = "접근성 프로필 수정",
            description = """
                    기존 접근성 프로필의 설정 정보를 수정합니다.

                    - 수정할 프로필 ID를 전달합니다.
                    - 변경할 접근성 설정 정보를 입력합니다.
                    - 수정된 프로필 정보를 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PatchMapping("/{profileId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Parameter(
                    description = "수정할 접근성 프로필 ID",
                    example = "1"
            )
            @PathVariable Long profileId,

            @Valid @RequestBody ProfileUpdateRequest request
    ) {
        ProfileResponse response = profileService.updateProfile(
                userId,
                profileId,
                request
        );

        return ResponseEntity.ok(
                ApiResponse.success("프로필 수정 성공", response)
        );
    }

    // 내 접근성 프로필 목록 조회
    @Operation(
            summary = "내 접근성 프로필 목록 조회",
            description = """
                    로그인한 사용자가 생성한 접근성 프로필 목록을 조회합니다.

                    - 사용자 계정에 등록된 접근성 프로필 목록을 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getMyProfiles(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId
    ) {
        List<ProfileResponse> responses =
                profileService.getMyProfiles(userId);

        return ResponseEntity.ok(
                ApiResponse.success("내 프로필 목록 조회 성공", responses)
        );
    }

    // 접근성 프로필 상세 조회
    @Operation(
            summary = "접근성 프로필 상세 조회",
            description = """
                    특정 접근성 프로필의 상세 정보를 조회합니다.

                    - 조회할 프로필 ID를 전달합니다.
                    - 해당 프로필의 접근성 설정 정보를 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @GetMapping("/{profileId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Parameter(
                    description = "조회할 접근성 프로필 ID",
                    example = "1"
            )
            @PathVariable Long profileId
    ) {
        ProfileResponse response =
                profileService.getProfile(userId, profileId);

        return ResponseEntity.ok(
                ApiResponse.success("프로필 조회 성공", response)
        );
    }

    // 프로필별 키링 조회
    @Operation(
            summary = "접근성 프로필별 NFC 키링 조회",
            description = """
                    특정 접근성 프로필에 연결된 NFC 키링 목록을 조회합니다.

                    - 조회할 접근성 프로필 ID를 전달합니다.
                    - 해당 프로필에 연결된 키링 정보를 목록으로 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @GetMapping("/{profileId}/keyrings")
    public ResponseEntity<ApiResponse<List<KeyringResponse>>> getProfileKeyrings(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Parameter(
                    description = "키링을 조회할 접근성 프로필 ID",
                    example = "1"
            )
            @PathVariable Long profileId
    ) {
        List<KeyringResponse> responses =
                profileService.getProfileKeyrings(userId, profileId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "프로필별 키링 조회 성공",
                        responses
                )
        );
    }
}