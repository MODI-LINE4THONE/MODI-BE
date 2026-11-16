package com.likelion.modibe.domain.kiosk.service;

import com.likelion.modibe.domain.kiosk.dto.KioskAccessibilityMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KioskWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    // 접근성 설정 적용
    public void sendAccessibilitySettings(
            String kioskCode,
            Long profileId,
            KioskAccessibilityMessage.AccessibilitySettings settings
    ) {

        KioskAccessibilityMessage message =
                new KioskAccessibilityMessage(
                        "ACCESSIBILITY_APPLY",
                        kioskCode,
                        profileId,
                        settings
                );

        messagingTemplate.convertAndSend(
                "/topic/kiosks/" + kioskCode,
                message
        );
    }

    // 접근성 설정 초기화
    public void resetAccessibilitySettings(String kioskCode) {

        KioskAccessibilityMessage message =
                new KioskAccessibilityMessage(
                        "ACCESSIBILITY_RESET",
                        kioskCode,
                        null,
                        null
                );

        messagingTemplate.convertAndSend(
                "/topic/kiosks/" + kioskCode,
                message
        );
    }
}