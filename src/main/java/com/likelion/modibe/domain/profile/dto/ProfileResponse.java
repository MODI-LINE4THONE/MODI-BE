package com.likelion.modibe.domain.profile.dto;

import com.likelion.modibe.domain.profile.entity.Profile;

public record ProfileResponse(
        Long profileId,
        String name,
        boolean largeText,
        boolean highContrast,
        boolean touchGuide,
        boolean lowerScreen,
        boolean touchCorrection
) {
    public static ProfileResponse from(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getName(),
                profile.isLargeText(),
                profile.isHighContrast(),
                profile.isTouchGuide(),
                profile.isLowerScreen(),
                profile.isTouchCorrection()
        );
    }
}