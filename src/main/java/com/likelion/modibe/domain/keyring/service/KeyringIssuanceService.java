package com.likelion.modibe.domain.keyring.service;

import com.likelion.modibe.domain.keyring.dto.KeyringIssuanceRequest;
import com.likelion.modibe.domain.keyring.dto.KeyringIssuanceResponse;
import com.likelion.modibe.domain.keyring.entity.IssuanceStatus;
import com.likelion.modibe.domain.keyring.entity.KeyringIssuance;
import com.likelion.modibe.domain.keyring.exception.KeyringErrorCode;
import com.likelion.modibe.domain.keyring.repository.KeyringIssuanceRepository;

import com.likelion.modibe.domain.user.entity.User;
import com.likelion.modibe.domain.user.repository.UserRepository;

import com.likelion.modibe.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyringIssuanceService {

    private final KeyringIssuanceRepository issuanceRepository;
    private final UserRepository userRepository;

    // 진행 중인 신청 상태
    private static final List<IssuanceStatus> ACTIVE_STATUSES = List.of(
            IssuanceStatus.REQUESTED,
            IssuanceStatus.APPROVED,
            IssuanceStatus.SHIPPING
    );

    // 키링 재발급 신청
    @Transactional
    public KeyringIssuanceResponse requestIssuance(
            Long userId,
            KeyringIssuanceRequest request
    ) {

        // 1. 로그인 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("사용자를 찾을 수 없습니다.")
                );

        // 2. 진행 중인 신청이 있는지 확인
        boolean alreadyRequested =
                issuanceRepository.existsByUserIdAndStatusIn(
                        userId,
                        ACTIVE_STATUSES
                );

        if (alreadyRequested) {
            throw new CustomException(
                    KeyringErrorCode.ISSUANCE_ALREADY_REQUESTED
            );
        }

        // 3. 재발급 신청 생성
        KeyringIssuance issuance = new KeyringIssuance(
                user,
                request.quantity(),
                request.postalCode(),
                request.address(),
                request.detailAddress()
        );

        // 4. DB 저장
        KeyringIssuance saved = issuanceRepository.save(issuance);

        // 5. 응답 반환
        return new KeyringIssuanceResponse(
                saved.getId(),
                saved.getStatus(),
                saved.getQuantity()
        );
    }

    // 내 재발급 신청 내역 조회
    @Transactional(readOnly = true)
    public List<KeyringIssuanceResponse> getMyIssuances(Long userId) {

        // 1. 사용자별 신청 내역 조회
        List<KeyringIssuance> issuances =
                issuanceRepository.findByUserIdOrderByCreatedAtDesc(userId);

        // 2. Entity → DTO 변환
        return issuances.stream()
                .map(issuance ->
                        new KeyringIssuanceResponse(
                                issuance.getId(),
                                issuance.getStatus(),
                                issuance.getQuantity()
                        )
                )
                .toList();
    }
}