package com.likelion.modibe.domain.user.dto;

import com.likelion.modibe.domain.user.entity.User;
import com.likelion.modibe.domain.user.entity.UserType;

public record UserResponse(

        Long userId,
        String loginId,
        UserType userType,
        boolean onboardingCompleted

) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getLoginId(),
                user.getUserType(),
                user.isOnboardingCompleted()
        );
    }
}
