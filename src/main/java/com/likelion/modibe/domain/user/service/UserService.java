
package com.likelion.modibe.domain.user.service;

import com.likelion.modibe.domain.profile.entity.Profile;
import com.likelion.modibe.domain.profile.repository.ProfileRepository;
import com.likelion.modibe.domain.user.dto.OnboardingRequest;
import com.likelion.modibe.domain.user.dto.OnboardingResponse;
import com.likelion.modibe.domain.user.dto.UserResponse;
import com.likelion.modibe.domain.user.entity.User;
import com.likelion.modibe.domain.user.entity.UserType;
import com.likelion.modibe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    // 온보딩 프로필 생성
    @Transactional
    public OnboardingResponse startOnboarding(
            Long userId,
            OnboardingRequest request
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 사용자입니다."
                        )
                );

        if (user.isOnboardingCompleted()) {
            throw new IllegalStateException(
                    "이미 온보딩을 완료한 사용자입니다."
            );
        }

        // 중복 온보딩 요청 방지
        if (!profileRepository.findByUserId(userId).isEmpty()) {
            throw new IllegalStateException(
                    "이미 생성된 프로필이 있습니다."
            );
        }

        String profileName;

        if (request.userType() == UserType.SELF) {
            profileName = "본인";
        } else {
            profileName = request.profileName();

            if (profileName == null ||
                    profileName.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "가족 프로필 별명은 필수입니다."
                );
            }

            profileName = profileName.trim();
        }

        // 사용자 유형 저장
        user.selectUserType(request.userType());

        // 최초 접근성 옵션은 기본값 false
        Profile profile = new Profile(
                user,
                profileName,
                false,
                false,
                false,
                false,
                false
        );

        Profile savedProfile =
                profileRepository.save(profile);

        return new OnboardingResponse(
                user.getId(),
                user.getUserType(),
                savedProfile.getId(),
                savedProfile.getName(),
                user.isOnboardingCompleted()
        );
    }

    // 온보딩 완료
    @Transactional
    public UserResponse completeOnboarding(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 사용자입니다."
                        )
                );

        if (profileRepository.findByUserId(userId).isEmpty()) {
            throw new IllegalStateException(
                    "프로필을 먼저 생성해야 합니다."
            );
        }

        user.completeOnboarding();

        return UserResponse.from(user);
    }

    // 내 정보 조회
    @Transactional(readOnly = true)
    public UserResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 사용자입니다."
                        )
                );

        return UserResponse.from(user);
    }
}
