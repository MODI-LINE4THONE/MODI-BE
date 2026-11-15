package com.likelion.modibe.domain.keyring.dto;

import com.likelion.modibe.domain.keyring.entity.Keyring;
import com.likelion.modibe.domain.keyring.entity.KeyringStatus;

import java.time.LocalDateTime;

public record KeyringResponse(
        Long keyringId,
        String registrationCode,
        KeyringStatus status,
        LocalDateTime registeredAt
) {
    public static KeyringResponse from(Keyring keyring) {
        return new KeyringResponse(
                keyring.getId(),
                keyring.getRegistrationCode(),
                keyring.getStatus(),
                keyring.getRegisteredAt()
        );
    }
}