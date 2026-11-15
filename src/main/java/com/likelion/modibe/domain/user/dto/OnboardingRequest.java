package com.likelion.modibe.domain.user.dto;

import com.likelion.modibe.domain.user.entity.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//가족보호자 request
public record OnboardingRequest(

        @NotNull
        UserType userType,

        @Size(max = 50)
        String profileName

) {}
