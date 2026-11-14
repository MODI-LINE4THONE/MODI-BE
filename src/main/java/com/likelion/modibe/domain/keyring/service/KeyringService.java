package com.likelion.modibe.domain.keyring.service;

import com.likelion.modibe.domain.keyring.dto.KeyringLostResponse;
import com.likelion.modibe.domain.keyring.dto.KeyringRegisterRequest;
import com.likelion.modibe.domain.keyring.entity.Keyring;
import com.likelion.modibe.domain.keyring.entity.KeyringStatus;
import com.likelion.modibe.domain.keyring.exception.KeyringErrorCode;
import com.likelion.modibe.domain.keyring.repository.KeyringRepository;
import com.likelion.modibe.domain.user.entity.User;
import com.likelion.modibe.domain.user.repository.UserRepository;
import com.likelion.modibe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.likelion.modibe.domain.keyring.dto.KeyringProfileConnectRequest;
import com.likelion.modibe.domain.keyring.dto.KeyringProfileConnectResponse;
import com.likelion.modibe.domain.profile.entity.Profile;
import com.likelion.modibe.domain.profile.exception.ProfileErrorCode;
import com.likelion.modibe.domain.profile.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
public class KeyringService {

    private final KeyringRepository keyringRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    // 키링 등록
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
                        new CustomException(
                                KeyringErrorCode.REGISTRATION_CODE_NOT_FOUND
                        )
                );

        if (keyring.getStatus() != KeyringStatus.AVAILABLE) {
            throw new CustomException(
                    KeyringErrorCode.KEYRING_ALREADY_REGISTERED
            );
        }

        keyring.register(user);

        return keyring.getId();
    }

    @Transactional
    public KeyringLostResponse reportLostKeyring(Long userId, Long keyringId) {

        // 1. 키링 조회
        Keyring keyring = keyringRepository.findById(keyringId)
                .orElseThrow(() ->
                        new CustomException(KeyringErrorCode.KEYRING_NOT_FOUND)
                );

        // 2. 키링 소유권 확인
        if (keyring.getUser() == null ||
                !keyring.getUser().getId().equals(userId)) {

            throw new CustomException(
                    KeyringErrorCode.KEYRING_ACCESS_DENIED
            );
        }

        // 3. 이미 분실된 키링인지 확인
        if (keyring.getStatus() == KeyringStatus.LOST) {

            throw new CustomException(
                    KeyringErrorCode.KEYRING_ALREADY_LOST
            );
        }

        // 4. 등록된 키링인지 확인
        if (keyring.getStatus() != KeyringStatus.REGISTERED) {

            throw new CustomException(
                    KeyringErrorCode.KEYRING_NOT_REGISTERED
            );
        }

        // 5. 키링 분실 처리
        keyring.markAsLost();

        return new KeyringLostResponse(
                keyring.getId(),
                keyring.getStatus()
        );
    }

    @Transactional
    public KeyringProfileConnectResponse connectExistingProfile(
            Long userId,
            Long keyringId,
            KeyringProfileConnectRequest request
    ) {

        // 1. 키링 조회
        Keyring keyring = keyringRepository.findById(keyringId)
                .orElseThrow(() ->
                        new CustomException(KeyringErrorCode.KEYRING_NOT_FOUND)
                );

        // 2. 키링 소유권 확인
        if (keyring.getUser() == null ||
                !keyring.getUser().getId().equals(userId)) {

            throw new CustomException(
                    KeyringErrorCode.KEYRING_ACCESS_DENIED
            );
        }

        // 3. 키링 상태 확인
        if (keyring.getStatus() == KeyringStatus.LOST) {
            throw new CustomException(
                    KeyringErrorCode.KEYRING_LOST_PROFILE_NOT_ALLOWED
            );
        }

        if (keyring.getStatus() != KeyringStatus.REGISTERED) {
            throw new CustomException(
                    KeyringErrorCode.KEYRING_PROFILE_CONNECTION_NOT_ALLOWED
            );
        }


        // 5. 프로필 조회
        Profile profile = profileRepository.findById(request.profileId())
                .orElseThrow(() ->
                        new CustomException(ProfileErrorCode.PROFILE_NOT_FOUND)
                );

        // 6. 프로필 소유권 확인
        if (!profile.getUser().getId().equals(userId)) {
            throw new CustomException(
                    ProfileErrorCode.PROFILE_ACCESS_DENIED
            );
        }

        // 7. 키링에 프로필 연결
        keyring.connectProfile(profile);

        // 8. 응답 반환
        return new KeyringProfileConnectResponse(
                keyring.getId(),
                profile.getId()
        );
    }
}