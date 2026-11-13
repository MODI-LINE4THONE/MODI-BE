package com.likelion.modibe.domain.keyring.dto;

import jakarta.validation.constraints.NotNull;

public record KeyringProfileConnectRequest(

        @NotNull(message = "프로필 ID를 입력해주세요.")
        Long profileId

) {
}