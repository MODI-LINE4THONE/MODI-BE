package com.likelion.modibe.domain.keyring.dto;

import com.likelion.modibe.domain.keyring.entity.IssuanceStatus;

public record KeyringIssuanceResponse(
        Long requestId,
        IssuanceStatus status,
        int quantity
) {
}