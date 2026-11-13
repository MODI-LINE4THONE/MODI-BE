package com.likelion.modibe.domain.profile.service;

import com.likelion.modibe.domain.keyring.dto.KeyringResponse;
import com.likelion.modibe.domain.keyring.repository.KeyringRepository;
import com.likelion.modibe.domain.profile.dto.ProfileCreateRequest;
import com.likelion.modibe.domain.profile.dto.ProfileUpdateRequest;
import com.likelion.modibe.domain.profile.entity.Profile;
import com.likelion.modibe.domain.profile.exception.ProfileErrorCode;
import com.likelion.modibe.domain.profile.repository.ProfileRepository;

import com.likelion.modibe.domain.user.entity.User;
import com.likelion.modibe.domain.user.repository.UserRepository;

import com.likelion.modibe.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.likelion.modibe.domain.profile.dto.ProfileResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final KeyringRepository keyringRepository;

    // 접근성 프로필 생성
    @Transactional
    public Long createProfile(
            Long userId,
            ProfileCreateRequest request
    ) {

        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 사용자입니다.")
                );

        // 2. 프로필 이름 공백 제거
        String name = request.name().trim();

        // 3. 이름 유효성 확인
        if (name.isBlank() || name.length() > 50) {
            throw new IllegalArgumentException(
                    "프로필 이름은 1자 이상 50자 이하여야 합니다."
            );
        }

        // 4. 동일 사용자 내 이름 중복 확인
        if (profileRepository.existsByUserIdAndName(userId, name)) {
            throw new CustomException(
                    ProfileErrorCode.PROFILE_NAME_DUPLICATED
            );
        }

        // 5. 접근성 프로필 생성
        Profile profile = new Profile(

                user,

                name,

                request.largeText(),

                request.highContrast(),

                request.touchGuide(),

                request.lowerScreen(),

                request.touchCorrection()

        );

        // 6. 프로필 저장
        Profile savedProfile = profileRepository.save(profile);

        return savedProfile.getId();
    }

    // 접근성 프로필 수정
    @Transactional
    public ProfileResponse updateProfile(
            Long userId,
            Long profileId,
            ProfileUpdateRequest request
    ) {

        // 1. 프로필 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() ->
                        new CustomException(ProfileErrorCode.PROFILE_NOT_FOUND)
                );

        // 2. 본인 프로필인지 확인
        if (!profile.getUser().getId().equals(userId)) {
            throw new CustomException(
                    ProfileErrorCode.PROFILE_ACCESS_DENIED
            );
        }

        // 3. 이름 변경 요청이 있는 경우
        String name = null;

        if (request.name() != null) {

            name = request.name().trim();

            // 이름 유효성 확인
            if (name.isBlank() || name.length() > 50) {
                throw new IllegalArgumentException(
                        "프로필 이름은 1자 이상 50자 이하여야 합니다."
                );
            }

            // 현재 프로필을 제외하고 이름 중복 확인
            if (profileRepository.existsByUserIdAndNameAndIdNot(
                    userId,
                    name,
                    profileId
            )) {
                throw new CustomException(
                        ProfileErrorCode.PROFILE_NAME_DUPLICATED
                );
            }
        }

        // 4. 접근성 설정 수정
        profile.update(
                name,
                request.largeText(),
                request.highContrast(),
                request.touchGuide(),
                request.lowerScreen(),
                request.touchCorrection()
        );

        // 5. 수정된 프로필 반환
        return ProfileResponse.from(profile);
    }

    @Transactional(readOnly = true)
    public List<ProfileResponse> getMyProfiles(Long userId) {

        return profileRepository.findByUserId(userId)
                .stream()
                .map(ProfileResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Long userId, Long profileId) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() ->
                        new CustomException(ProfileErrorCode.PROFILE_NOT_FOUND)
                );

        // 본인 프로필인지 확인
        if (!profile.getUser().getId().equals(userId)) {
            throw new CustomException(
                    ProfileErrorCode.PROFILE_ACCESS_DENIED
            );
        }

        return ProfileResponse.from(profile);
    }

    @Transactional(readOnly = true)
    public List<KeyringResponse> getProfileKeyrings(
            Long userId,
            Long profileId
    ) {

        // 1. 프로필 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() ->
                        new CustomException(ProfileErrorCode.PROFILE_NOT_FOUND)
                );

        // 2. 본인 프로필인지 확인
        if (!profile.getUser().getId().equals(userId)) {
            throw new CustomException(
                    ProfileErrorCode.PROFILE_ACCESS_DENIED
            );
        }

        // 3. 프로필에 연결된 키링 조회
        return keyringRepository.findByProfileId(profileId)
                .stream()
                .map(KeyringResponse::from)
                .toList();
    }
}