
package com.likelion.modibe.domain.auth.controller;

import com.likelion.modibe.domain.auth.dto.CheckIdResponse;
import com.likelion.modibe.domain.auth.dto.LoginRequest;
import com.likelion.modibe.domain.auth.dto.LoginResponse;
import com.likelion.modibe.domain.auth.dto.SignupRequest;
import com.likelion.modibe.domain.auth.service.AuthService;
import com.likelion.modibe.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @Valid @RequestBody SignupRequest request) {

        authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("회원가입 성공"));
    }

    @GetMapping("/check-id")
    public ResponseEntity<ApiResponse<CheckIdResponse>> checkId(
            @RequestParam String loginId) {

        CheckIdResponse response = authService.checkId(loginId);

        return ResponseEntity.ok(
                ApiResponse.success("아이디 중복 확인 완료", response)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("로그인 성공", response)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {

        return ResponseEntity.ok(
                ApiResponse.success("로그아웃 요청 처리 완료")
        );
    }


}
