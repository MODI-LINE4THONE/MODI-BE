package com.likelion.modibe.domain.keyring.repository;

import com.likelion.modibe.domain.keyring.entity.Keyring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeyringRepository extends JpaRepository<Keyring, Long> {

    Optional<Keyring> findByRegistrationCode(String registrationCode);

    List<Keyring> findByProfileId(Long profileId);

}