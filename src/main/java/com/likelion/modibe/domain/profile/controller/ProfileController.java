package com.likelion.modibe.domain.profile.controller;

import com.likelion.modibe.domain.profile.dto.ProfileCreateRequest;
import com.likelion.modibe.domain.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> createProfile(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody ProfileCreateRequest request
    ) {

        Long profileId = profileService.createProfile(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("profileId", profileId));
    }
}