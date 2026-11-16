package com.likelion.modibe.domain.kiosk.dto;

public record KioskAccessibilityMessage(

        String type,

        String kioskCode,

        Long profileId,

        AccessibilitySettings settings

) {

    public record AccessibilitySettings(

            boolean largeText,

            boolean highContrast,

            boolean touchGuide,

            boolean lowerScreen,

            boolean touchCorrection

    ) {}

}