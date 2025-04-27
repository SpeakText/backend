package com.speaktext.backend.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(max = 64, message = "아이디는 최대 64자까지 가능합니다.")
    String id,

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password
) {
}
