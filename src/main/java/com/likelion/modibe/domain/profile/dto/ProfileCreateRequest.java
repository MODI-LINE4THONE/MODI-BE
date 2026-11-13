package com.likelion.modibe.domain.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfileCreateRequest(

        @NotNull
        Long keyringId,

        @NotBlank
        @Size(max = 50)
        String name,

        boolean largeText,
        boolean highContrast,
        boolean simplifiedMenu,
        boolean lowerScreen,
        boolean touchGuide

) {}
