package com.likelion.modibe.domain.profile.controller;

import com.likelion.modibe.domain.keyring.dto.KeyringResponse;
import com.likelion.modibe.domain.profile.dto.ProfileCreateRequest;
import com.likelion.modibe.domain.profile.dto.ProfileResponse;
import com.likelion.modibe.domain.profile.dto.ProfileUpdateRequest;
import com.likelion.modibe.domain.profile.service.ProfileService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    // 접근성 프로필 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Long>>> createProfile(
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
    @PatchMapping("/{profileId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @AuthenticationPrincipal Long userId,
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
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getMyProfiles(
            @AuthenticationPrincipal Long userId
    ) {
        List<ProfileResponse> responses =
                profileService.getMyProfiles(userId);

        return ResponseEntity.ok(
                ApiResponse.success("내 프로필 목록 조회 성공", responses)
        );
    }

    // 접근성 프로필 상세 조회
    @GetMapping("/{profileId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long profileId
    ) {
        ProfileResponse response =
                profileService.getProfile(userId, profileId);

        return ResponseEntity.ok(
                ApiResponse.success("프로필 조회 성공", response)
        );
    }

    // 프로필별 키링 조회
    @GetMapping("/{profileId}/keyrings")
    public ResponseEntity<ApiResponse<List<KeyringResponse>>> getProfileKeyrings(
            @AuthenticationPrincipal Long userId,
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