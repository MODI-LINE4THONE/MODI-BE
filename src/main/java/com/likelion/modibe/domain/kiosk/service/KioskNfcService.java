package com.likelion.modibe.domain.kiosk.service;

import com.likelion.modibe.domain.keyring.entity.Keyring;
import com.likelion.modibe.domain.keyring.entity.KeyringStatus;
import com.likelion.modibe.domain.keyring.exception.KeyringErrorCode;
import com.likelion.modibe.domain.keyring.repository.KeyringRepository;
import com.likelion.modibe.domain.kiosk.dto.KioskAccessibilityMessage;
import com.likelion.modibe.domain.kiosk.dto.KioskNfcResponse;
import com.likelion.modibe.domain.kiosk.exception.KioskErrorCode;
import com.likelion.modibe.domain.kiosk.repository.KioskRepository;
import com.likelion.modibe.domain.profile.entity.Profile;
import com.likelion.modibe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class KioskNfcService {

    private final KioskRepository kioskRepository;
    private final KeyringRepository keyringRepository;
    private final KioskWebSocketService kioskWebSocketService;

    // NFC 태그 인식 및 접근성 설정 적용
    @Transactional(readOnly = true)
    public KioskNfcResponse handleNfcTag(String kioskCode, String uid) {

        // 1. 키오스크 존재 여부 확인
        if (!kioskRepository.existsByKioskCode(kioskCode)) {
            throw new CustomException(
                    KioskErrorCode.KIOSK_NOT_FOUND
            );
        }

        // 2. NFC UID로 키링 조회
        Keyring keyring = keyringRepository.findByUid(uid)
                .orElseThrow(() ->
                        new CustomException(
                                KeyringErrorCode.KEYRING_NOT_FOUND
                        )
                );

        // 3. 키링 상태 확인
        if (keyring.getStatus() == KeyringStatus.AVAILABLE) {
            throw new CustomException(
                    KioskErrorCode.NFC_KEYRING_NOT_REGISTERED
            );
        }

        if (keyring.getStatus() != KeyringStatus.REGISTERED) {
            throw new CustomException(
                    KioskErrorCode.NFC_KEYRING_UNAVAILABLE
            );
        }

        // 4. 연결된 프로필 확인
        Profile profile = keyring.getProfile();

        if (profile == null) {
            throw new CustomException(
                    KioskErrorCode.PROFILE_NOT_CONNECTED
            );
        }

        // 5. 접근성 설정 DTO 생성
        KioskAccessibilityMessage.AccessibilitySettings settings =
                new KioskAccessibilityMessage.AccessibilitySettings(
                        profile.isLargeText(),
                        profile.isHighContrast(),
                        profile.isTouchGuide(),
                        profile.isLowerScreen(),
                        profile.isTouchCorrection()
                );

        Long profileId = profile.getId();

        // 6. Response Body 생성
        KioskNfcResponse response = KioskNfcResponse.from(
                kioskCode,
                profile
        );

        // 7. 트랜잭션 완료 후 WebSocket 메시지 전송
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        kioskWebSocketService.sendAccessibilitySettings(
                                kioskCode,
                                profileId,
                                settings
                        );
                    }
                }
        );

        return response;
    }
}