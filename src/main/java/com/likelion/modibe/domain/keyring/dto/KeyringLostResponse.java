package com.likelion.modibe.domain.keyring.dto;

import com.likelion.modibe.domain.keyring.entity.KeyringStatus;

public record KeyringLostResponse(
        Long keyringId,
        KeyringStatus status
) {
}