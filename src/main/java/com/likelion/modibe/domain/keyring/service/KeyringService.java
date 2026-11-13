package com.likelion.modibe.domain.keyring.service;

import com.likelion.modibe.domain.keyring.dto.KeyringRegisterRequest;
import com.likelion.modibe.domain.keyring.entity.Keyring;
import com.likelion.modibe.domain.keyring.repository.KeyringRepository;

import com.likelion.modibe.domain.profile.repository.ProfileRepository;
import com.likelion.modibe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.likelion.modibe.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeyringService {

    private final KeyringRepository keyringRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long registerKeyring(
            Long userId,
            KeyringRegisterRequest request
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("사용자를 찾을 수 없습니다.")
                );

        Keyring keyring = keyringRepository
                .findByRegistrationCode(request.registrationCode())
                .orElseThrow(() ->
                        new IllegalArgumentException("등록 코드를 찾을 수 없습니다.")
                );

        keyring.register(user);

        System.out.println("등록 후 status = " + keyring.getStatus());
        System.out.println("등록 후 userId = " + keyring.getUser().getId());

        keyringRepository.flush();

        return keyring.getId();
    }
}
