package com.likelion.modibe.domain.keyring.controller;

import com.likelion.modibe.domain.keyring.dto.KeyringIssuanceRequest;
import com.likelion.modibe.domain.keyring.dto.KeyringIssuanceResponse;
import com.likelion.modibe.domain.keyring.service.KeyringIssuanceService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(
        name = "키링 관리",
        description = "NFC 키링 등록, 분실 신고, 재발급 및 접근성 프로필 연결 API"
)
public class KeyringIssuanceController {

    private final KeyringIssuanceService issuanceService;

    // 키링 재발급 신청
    @Operation(
            summary = "NFC 키링 재발급 신청",
            description = """
                    분실한 NFC 키링의 재발급을 신청합니다.

                    - 재발급 신청 시 필요한 정보를 입력합니다.
                    - 이미 진행 중인 재발급 신청이 있다면 중복 신청이 제한됩니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PostMapping
    public ResponseEntity<ApiResponse<KeyringIssuanceResponse>> requestIssuance(
            @Parameter(hidden = true)
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
    @Operation(
            summary = "내 키링 재발급 신청 내역 조회",
            description = """
                    로그인한 사용자의 NFC 키링 재발급 신청 내역을 조회합니다.

                    - 본인이 신청한 재발급 내역을 목록으로 반환합니다.
                    - 신청 내역이 없는 경우 빈 목록을 반환합니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<KeyringIssuanceResponse>>> getMyIssuances(
            @Parameter(hidden = true)
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