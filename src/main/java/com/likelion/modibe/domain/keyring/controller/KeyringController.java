package com.likelion.modibe.domain.keyring.controller;

import com.likelion.modibe.domain.keyring.dto.*;
import com.likelion.modibe.domain.keyring.service.KeyringService;
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
@RequestMapping("/api/keyrings")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "키링 관리",
        description = "NFC 키링 등록, 분실 신고 및 접근성 프로필 연결 API"
)
public class KeyringController {

    private final KeyringService keyringService;

    // 키링 등록
    @Operation(
            summary = "NFC 키링 등록",
            description = """
                    발급받은 NFC 키링의 등록 코드를 이용하여
                    로그인한 사용자 계정에 키링을 등록합니다.

                    - 이미 등록된 키링은 중복 등록할 수 없습니다.
                    - 등록 가능한 상태의 키링만 등록할 수 있습니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PostMapping("/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<KeyringResponse>> register(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Valid @RequestBody KeyringRegisterRequest request
    ) {
        KeyringResponse response =
                keyringService.registerKeyring(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "키링 등록 성공",
                        response
                ));
    }

    // 키링 분실 신고
    @Operation(
            summary = "NFC 키링 분실 신고",
            description = """
                    로그인한 사용자가 소유한 NFC 키링을 분실 처리합니다.

                    - 분실된 키링은 키오스크에서 사용할 수 없습니다.
                    - 본인 소유의 키링만 분실 신고할 수 있습니다.
                    - 이미 분실된 키링은 중복 신고할 수 없습니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PatchMapping("/{keyringId}/lost")
    public ResponseEntity<ApiResponse<KeyringLostResponse>> reportLostKeyring(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Parameter(
                    description = "분실 신고할 키링 ID",
                    example = "1"
            )
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
    @Operation(
            summary = "NFC 키링에 접근성 프로필 연결",
            description = """
                    로그인한 사용자가 생성한 기존 접근성 프로필을
                    본인 소유의 NFC 키링에 연결합니다.

                    - 등록된 키링에만 프로필을 연결할 수 있습니다.
                    - 분실된 키링에는 프로필을 연결할 수 없습니다.
                    - 이미 다른 프로필이 연결된 키링에는 연결할 수 없습니다.
                    - 연결된 프로필은 NFC 태그 인식 시 키오스크에 적용됩니다.
                    - JWT 인증이 필요합니다.
                    """
    )
    @PatchMapping("/{keyringId}/profile")
    public ResponseEntity<ApiResponse<KeyringProfileConnectResponse>> connectProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Parameter(
                    description = "프로필을 연결할 키링 ID",
                    example = "1"
            )
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