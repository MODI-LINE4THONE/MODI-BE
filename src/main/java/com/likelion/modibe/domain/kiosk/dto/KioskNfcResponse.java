package com.likelion.modibe.domain.kiosk.dto;

import com.likelion.modibe.domain.profile.entity.Profile;

public record KioskNfcResponse(
        String kioskCode,
        String status,
        Long profileId,
        String profileName,
        AccessibilitySettings settings
) {
    public record AccessibilitySettings(
            boolean largeText,
            boolean highContrast,
            boolean touchGuide,
            boolean lowerScreen,
            boolean touchCorrection
    ) {}

    public static KioskNfcResponse from(
            String kioskCode,
            Profile profile
    ) {
        return new KioskNfcResponse(
                kioskCode,
                "APPLIED",
                profile.getId(),
                profile.getName(),
                new AccessibilitySettings(
                        profile.isLargeText(),
                        profile.isHighContrast(),
                        profile.isTouchGuide(),
                        profile.isLowerScreen(),
                        profile.isTouchCorrection()
                )
        );
    }
}