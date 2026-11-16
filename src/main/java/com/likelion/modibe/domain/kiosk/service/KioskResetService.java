package com.likelion.modibe.domain.kiosk.service;

import com.likelion.modibe.domain.kiosk.dto.KioskResetResponse;
import com.likelion.modibe.domain.kiosk.repository.KioskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KioskResetService {

    private final KioskRepository kioskRepository;
    private final KioskWebSocketService kioskWebSocketService;

    // 키오스크 접근성 설정 초기화
    public KioskResetResponse resetAccessibility(String kioskCode) {

        // 1. 키오스크 존재 여부 확인
        if (!kioskRepository.existsByKioskCode(kioskCode)) {
            throw new IllegalArgumentException("존재하지 않는 키오스크입니다.");
        }

        // 2. WebSocket으로 초기화 메시지 전송
        kioskWebSocketService.resetAccessibilitySettings(kioskCode);

        // 3. Response Body 반환
        return KioskResetResponse.of(kioskCode);
    }
}