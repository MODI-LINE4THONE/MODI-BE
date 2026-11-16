package com.likelion.modibe.domain.kiosk.repository;

import com.likelion.modibe.domain.kiosk.entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KioskRepository extends JpaRepository<Kiosk, Long> {

    Optional<Kiosk> findByKioskCode(String kioskCode);

    boolean existsByKioskCode(String kioskCode);
}