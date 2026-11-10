
package com.likelion.modibe.domain.auth.service;

import com.likelion.modibe.domain.auth.dto.CheckIdResponse;
import com.likelion.modibe.domain.auth.dto.SignupRequest;
import com.likelion.modibe.domain.user.entity.User;
import com.likelion.modibe.domain.user.exception.UserErrorCode;
import com.likelion.modibe.domain.user.repository.UserRepository;
import com.likelion.modibe.global.exception.CustomException;
import com.likelion.modibe.domain.auth.dto.LoginRequest;
import com.likelion.modibe.domain.auth.dto.LoginResponse;
import com.likelion.modibe.domain.auth.exception.AuthErrorCode;
import com.likelion.modibe.global.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(SignupRequest request) {

        // 아이디 중복 검사
        if (userRepository.existsByLoginId(request.loginId())) {
            throw new CustomException(
                    UserErrorCode.DUPLICATE_LOGIN_ID
            );
        }

        String encodedPassword =
                passwordEncoder.encode(request.password());

        User user = new User(
                request.loginId(),
                encodedPassword
        );

        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public CheckIdResponse checkId(String loginId) {

        boolean exists = userRepository.existsByLoginId(loginId);

        return new CheckIdResponse(!exists);
    }



    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        // 사용자 조회
        User user = userRepository
                .findByLoginId(request.loginId())
                .orElseThrow(() -> new CustomException(
                        AuthErrorCode.INVALID_CREDENTIALS
                ));

        // 비밀번호 검증
        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new CustomException(
                    AuthErrorCode.INVALID_CREDENTIALS
            );
        }

        // JWT Access Token
        String accessToken =
                jwtTokenProvider.createAccessToken(user.getId());

        return new LoginResponse(
                accessToken,
                "Bearer",
                jwtTokenProvider.getAccessTokenExpirationSeconds()
        );
    }
}
