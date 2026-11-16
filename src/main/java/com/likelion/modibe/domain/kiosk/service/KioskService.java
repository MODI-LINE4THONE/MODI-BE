package com.likelion.modibe.domain.kiosk.service;

import com.likelion.modibe.domain.kiosk.entity.Kiosk;
import com.likelion.modibe.domain.kiosk.repository.KioskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KioskService {

    private final KioskRepository kioskRepository;

    // 키오스크 코드로 조회
    @Transactional(readOnly = true)
    public Kiosk getKiosk(String kioskCode) {
        return kioskRepository.findByKioskCode(kioskCode)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 키오스크입니다.")
                );
    }

    // 키오스크 연결 종료
    @Transactional
    public void disconnect(String kioskCode) {

        Kiosk kiosk = getKiosk(kioskCode);

        kiosk.disconnect();
    }
}