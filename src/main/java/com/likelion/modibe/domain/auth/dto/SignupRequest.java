
package com.likelion.modibe.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 4, max = 15,
                message = "아이디는 4~15야 합니다.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 30,
                message = "비밀번호는 8자 이상여야 합니다.")
        String password
) {
}
