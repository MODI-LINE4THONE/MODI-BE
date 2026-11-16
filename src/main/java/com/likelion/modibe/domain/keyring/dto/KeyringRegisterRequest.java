package com.likelion.modibe.domain.keyring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record KeyringRegisterRequest(

        @NotBlank
        @Pattern(regexp = "^[A-Z0-9]{6}$")
        String registrationCode

) {}