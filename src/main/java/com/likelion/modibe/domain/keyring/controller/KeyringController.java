package com.likelion.modibe.domain.keyring.controller;

import com.likelion.modibe.domain.keyring.dto.KeyringRegisterRequest;
import com.likelion.modibe.domain.keyring.service.KeyringService;
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
@RequestMapping("/api/keyrings")
@SecurityRequirement(name = "bearerAuth")
public class KeyringController {

    private final KeyringService keyringService;

    @PostMapping("/registrations")
    public ResponseEntity<Map<String, Long>> register(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody KeyringRegisterRequest request
    ) {

        Long keyringId = keyringService.registerKeyring(userId, request);
        System.out.println("키링 등록 userId = " + userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("keyringId", keyringId));
    }

}