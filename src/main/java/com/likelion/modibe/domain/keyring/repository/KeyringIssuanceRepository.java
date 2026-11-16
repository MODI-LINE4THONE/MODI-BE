package com.likelion.modibe.domain.keyring.repository;

import com.likelion.modibe.domain.keyring.entity.IssuanceStatus;
import com.likelion.modibe.domain.keyring.entity.KeyringIssuance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface KeyringIssuanceRepository
        extends JpaRepository<KeyringIssuance, Long> {

    // 로그인한 사용자의 신청 내역 조회 (최신순)
    List<KeyringIssuance> findByUserIdOrderByCreatedAtDesc(Long userId);

    // 진행 중인 발급 신청이 존재하는지 확인
    boolean existsByUserIdAndStatusIn(
            Long userId,
            Collection<IssuanceStatus> statuses
    );
}