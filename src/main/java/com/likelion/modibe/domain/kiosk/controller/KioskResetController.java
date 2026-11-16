package com.likelion.modibe.domain.kiosk.controller;

import com.likelion.modibe.domain.kiosk.dto.KioskResetResponse;
import com.likelion.modibe.domain.kiosk.service.KioskResetService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "키오스크 NFC",
        description = "NFC 키링 인식 및 키오스크 접근성 설정 관리 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kiosks")
public class KioskResetController {

    private final KioskResetService kioskResetService;

    // 키오스크 접근성 설정 초기화
    @Operation(
            summary = "키오스크 접근성 설정 초기화",
            description = """
                    키오스크에 적용된 사용자 접근성 설정을 초기화합니다.

                    - 주문 완료 또는 사용자 이용 종료 시 호출합니다.
                    - WebSocket을 통해 ACCESSIBILITY_RESET 메시지를 전송합니다.
                    - 초기화 메시지를 수신한 키오스크는 기본 UI로 복귀합니다.
                    - WebSocket 구독 주소: /topic/kiosks/{kioskCode}
                    """
    )
    @PostMapping("/{kioskCode}/reset")
    public ResponseEntity<ApiResponse<KioskResetResponse>> resetAccessibility(

            @Parameter(
                    description = "접근성 설정을 초기화할 키오스크 코드",
                    example = "KIOSK-001"
            )
            @PathVariable String kioskCode
    ) {
        KioskResetResponse response =
                kioskResetService.resetAccessibility(kioskCode);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "키오스크 접근성 설정 초기화 요청이 완료되었습니다.",
                        response
                )
        );
    }
}