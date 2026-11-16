package com.likelion.modibe.domain.keyring.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record KeyringIssuanceRequest(

        @NotNull(message = "발급 수량을 입력해주세요.")
        @Min(value = 1, message = "최소 1개 이상 신청해야 합니다.")
        @Max(value = 10, message = "최대 10개까지 신청할 수 있습니다.")
        Integer quantity,

        @NotBlank(message = "우편번호를 입력해주세요.")
        @Pattern(
                regexp = "^[0-9]{5}$",
                message = "우편번호는 숫자 5자리여야 합니다."
        )
        String postalCode,

        @NotBlank(message = "주소를 입력해주세요.")
        String address,

        @NotBlank(message = "상세 주소를 입력해주세요.")
        String detailAddress
) {
}