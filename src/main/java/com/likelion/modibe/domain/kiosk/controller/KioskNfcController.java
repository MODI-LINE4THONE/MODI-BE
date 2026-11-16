package com.likelion.modibe.domain.kiosk.controller;

import com.likelion.modibe.domain.kiosk.dto.KioskNfcRequest;
import com.likelion.modibe.domain.kiosk.dto.KioskNfcResponse;
import com.likelion.modibe.domain.kiosk.service.KioskNfcService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "키오스크 NFC",
        description = "NFC 키링 인식 및 접근성 설정 적용 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kiosks")
public class KioskNfcController {

    private final KioskNfcService kioskNfcService;

    // NFC 키링 인식 및 접근성 설정 적용
    @Operation(
            summary = "NFC 키링 인식 및 접근성 설정 적용",
            description = """
                    키오스크에서 NFC 키링을 태그하면 UID를 통해
                    연결된 접근성 프로필을 조회합니다.

                    - 등록된 NFC 키링만 사용할 수 있습니다.
                    - 접근성 프로필이 연결된 키링만 사용할 수 있습니다.
                    - 조회된 접근성 설정은 WebSocket을 통해 실시간으로 전달됩니다.
                    - WebSocket 구독 주소: /topic/kiosks/{kioskCode}
                    """
    )
    @PostMapping("/{kioskCode}/nfc")
    public ResponseEntity<ApiResponse<KioskNfcResponse>> handleNfcTag(

            @Parameter(
                    description = "접근성 설정을 적용할 키오스크 코드",
                    example = "KIOSK-001"
            )
            @PathVariable String kioskCode,

            @Valid @RequestBody KioskNfcRequest request
    ) {
        KioskNfcResponse response =
                kioskNfcService.handleNfcTag(
                        kioskCode,
                        request.uid()
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "접근성 설정이 성공적으로 적용되었습니다.",
                        response
                )
        );
    }
}