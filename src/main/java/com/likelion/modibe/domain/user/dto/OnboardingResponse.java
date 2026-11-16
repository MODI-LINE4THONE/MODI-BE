package com.likelion.modibe.domain.user.dto;

import com.likelion.modibe.domain.user.entity.UserType;


//온보딩에서 생성된 프로필 ID를 반환해서 키링 연결에 사용
public record OnboardingResponse(
        Long userId,
        UserType userType,
        Long profileId,
        String profileName,
        boolean onboardingCompleted
) {}