package com.likelion.modibe.domain.keyring.controller;

import com.likelion.modibe.domain.keyring.dto.KeyringLostResponse;
import com.likelion.modibe.domain.keyring.dto.KeyringRegisterRequest;
import com.likelion.modibe.domain.keyring.dto.KeyringProfileConnectRequest;
import com.likelion.modibe.domain.keyring.dto.KeyringProfileConnectResponse;
import com.likelion.modibe.domain.keyring.service.KeyringService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keyrings")
@SecurityRequirement(name = "bearerAuth")
public class KeyringController {

    private final KeyringService keyringService;

    // 키링 등록
    @PostMapping("/registrations")
    public ResponseEntity<ApiResponse<Map<String, Long>>> register(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody KeyringRegisterRequest request
    ) {
        Long keyringId =
                keyringService.registerKeyring(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "키링 등록 성공",
                        Map.of("keyringId", keyringId)
                ));
    }

    // 키링 분실 신고
    @PatchMapping("/{keyringId}/lost")
    public ResponseEntity<ApiResponse<KeyringLostResponse>> reportLostKeyring(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long keyringId
    ) {
        KeyringLostResponse response =
                keyringService.reportLostKeyring(userId, keyringId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "키링 분실 처리가 완료되었습니다.",
                        response
                )
        );
    }

    // 키링에 기존 접근성 프로필 연결
    @PatchMapping("/{keyringId}/profile")
    public ResponseEntity<ApiResponse<KeyringProfileConnectResponse>> connectProfile(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long keyringId,
            @Valid @RequestBody KeyringProfileConnectRequest request
    ) {
        KeyringProfileConnectResponse response =
                keyringService.connectExistingProfile(
                        userId,
                        keyringId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "기존 접근성 프로필이 새 키링에 연결되었습니다.",
                        response
                )
        );
    }
}