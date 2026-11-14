package com.likelion.modibe.domain.keyring.controller;

import com.likelion.modibe.domain.keyring.dto.KeyringIssuanceRequest;
import com.likelion.modibe.domain.keyring.dto.KeyringIssuanceResponse;
import com.likelion.modibe.domain.keyring.service.KeyringIssuanceService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keyrings/issuance-requests")
@SecurityRequirement(name = "bearerAuth")
public class KeyringIssuanceController {

    private final KeyringIssuanceService issuanceService;

    // 키링 재발급 신청
    @PostMapping
    public ResponseEntity<ApiResponse<KeyringIssuanceResponse>> requestIssuance(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody KeyringIssuanceRequest request
    ) {
        KeyringIssuanceResponse response =
                issuanceService.requestIssuance(userId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "키링 재발급 신청이 완료되었습니다.",
                        response
                )
        );
    }

    // 내 재발급 신청 내역 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<KeyringIssuanceResponse>>> getMyIssuances(
            @AuthenticationPrincipal Long userId
    ) {
        List<KeyringIssuanceResponse> responses =
                issuanceService.getMyIssuances(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "키링 발급 신청 내역 조회에 성공했습니다.",
                        responses
                )
        );
    }
}