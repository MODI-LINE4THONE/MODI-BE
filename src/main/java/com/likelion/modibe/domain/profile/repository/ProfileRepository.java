package com.likelion.modibe.domain.profile.repository;

import com.likelion.modibe.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findByUserId(Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndName(Long userId, String name);

    boolean existsByUserIdAndNameAndIdNot(
            Long userId,
            String name,
            Long profileId
    );
}