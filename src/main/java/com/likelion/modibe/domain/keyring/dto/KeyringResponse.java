package com.likelion.modibe.domain.keyring.dto;

import com.likelion.modibe.domain.keyring.entity.Keyring;
import com.likelion.modibe.domain.keyring.entity.KeyringStatus;

public record KeyringResponse(
        Long keyringId,
        String registrationCode,
        KeyringStatus status
) {
    public static KeyringResponse from(Keyring keyring) {
        return new KeyringResponse(
                keyring.getId(),
                keyring.getRegistrationCode(),
                keyring.getStatus()
        );
    }
}