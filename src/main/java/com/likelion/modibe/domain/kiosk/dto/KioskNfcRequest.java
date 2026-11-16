package com.likelion.modibe.domain.kiosk.dto;

import jakarta.validation.constraints.NotBlank;

public record KioskNfcRequest(
        @NotBlank String uid
) {}