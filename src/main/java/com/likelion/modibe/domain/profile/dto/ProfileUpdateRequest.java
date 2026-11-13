package com.likelion.modibe.domain.profile.dto;

import jakarta.validation.constraints.Size;

public record ProfileUpdateRequest(

        @Size(min = 1, max = 50)
        String name,

        Boolean largeText,
        Boolean highContrast,
        Boolean touchGuide,
        Boolean lowerScreen,
        Boolean touchCorrection

) {}