package kr.co.olivepay.auth.dto.req;

import jakarta.validation.constraints.NotBlank;

public record RefreshReq(
        @NotBlank(message = "accessToken 은 빈 값일 수 없습니다.")
        String accessToken,

        @NotBlank(message = "refreshToken 은 빈 값일 수 없습니다.")
        String refreshToken
) { }