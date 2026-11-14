package com.likelion.modibe.domain.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfileCreateRequest(

        @NotBlank
        @Size(max = 50)
        String name,

        boolean largeText,
        boolean highContrast,
        boolean touchGuide,
        boolean lowerScreen,
        boolean touchCorrection

) {}