package com.likelion.modibe.domain.auth.controller;

import com.likelion.modibe.domain.auth.dto.CheckIdResponse;
import com.likelion.modibe.domain.auth.dto.LoginRequest;
import com.likelion.modibe.domain.auth.dto.LoginResponse;
import com.likelion.modibe.domain.auth.dto.SignupRequest;
import com.likelion.modibe.domain.auth.service.AuthService;
import com.likelion.modibe.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(
        name = "회원 인증",
        description = "회원가입, 아이디 중복 확인, 로그인 및 로그아웃 API"
)
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @Operation(
            summary = "회원가입",
            description = """
                    새로운 사용자 계정을 생성합니다.

                    - 회원가입에 필요한 정보를 입력합니다.
                    - 아이디 중복 여부 및 입력값을 검증합니다.
                    - 회원가입 성공 시 201 Created를 반환합니다.
                    - JWT 인증이 필요하지 않습니다.
                    """
    )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("회원가입 성공"));
    }

    // 아이디 중복 확인
    @Operation(
            summary = "아이디 중복 확인",
            description = """
                    회원가입 시 사용할 로그인 아이디의 중복 여부를 확인합니다.

                    - 확인할 아이디를 loginId로 전달합니다.
                    - 중복 확인 결과를 반환합니다.
                    - JWT 인증이 필요하지 않습니다.
                    """
    )
    @GetMapping("/check-id")
    public ResponseEntity<ApiResponse<CheckIdResponse>> checkId(

            @Parameter(
                    description = "중복 확인할 로그인 아이디",
                    example = "modi1234"
            )
            @RequestParam String loginId
    ) {
        CheckIdResponse response = authService.checkId(loginId);

        return ResponseEntity.ok(
                ApiResponse.success("아이디 중복 확인 완료", response)
        );
    }

    // 로그인
    @Operation(
            summary = "로그인",
            description = """
                    사용자 아이디와 비밀번호를 이용하여 로그인합니다.

                    - 로그인 정보를 검증합니다.
                    - 인증 성공 시 로그인 결과를 반환합니다.
                    - JWT 인증이 필요하지 않습니다.
                    """
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("로그인 성공", response)
        );
    }

    // 로그아웃
    @Operation(
            summary = "로그아웃",
            description = """
                    사용자 로그아웃 요청을 처리합니다.

                    - 로그아웃 요청에 대한 성공 응답을 반환합니다.
                    """
    )
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {

        return ResponseEntity.ok(
                ApiResponse.success("로그아웃 요청 처리 완료")
        );
    }
}