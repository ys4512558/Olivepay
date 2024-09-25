package kr.co.olivepay.auth.dto.res;

import lombok.Builder;

@Builder
public record UserLoginRes (
        String accessToken,
        String refreshToken,
        String role
) { }
