
package com.likelion.modibe.domain.profile.service;

import com.likelion.modibe.domain.keyring.entity.Keyring;
import com.likelion.modibe.domain.keyring.repository.KeyringRepository;
import com.likelion.modibe.domain.profile.dto.ProfileCreateRequest;
import com.likelion.modibe.domain.profile.entity.Profile;
import com.likelion.modibe.domain.profile.repository.ProfileRepository;
import com.likelion.modibe.domain.user.entity.User;
import com.likelion.modibe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final KeyringRepository keyringRepository;

    @Transactional
    public Long createProfile(
            Long userId,
            ProfileCreateRequest request
    ) {
        // 로그인 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 사용자입니다.")
                );

        // 등록된 키링 조회
        Keyring keyring = keyringRepository
                .findById(request.keyringId())
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 키링입니다.")
                );

        if (keyring.getUser() == null ||
                !keyring.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException(
                    "본인의 키링이 아닙니다."
            );
        }

        // 프로필 생성
        Profile profile = new Profile(
                user,
                request.name(),
                request.largeText(),
                request.highContrast(),
                request.simplifiedMenu(),
                request.lowerScreen(),
                request.touchGuide()
        );

        Profile savedProfile = profileRepository.save(profile);

        keyring.connectProfile(savedProfile);

        return savedProfile.getId();
    }
}
