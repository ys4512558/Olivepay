package kr.co.olivepay.auth.dto.res;

import lombok.Builder;

@Builder
public record RefreshRes(
        String accessToken,
        String refreshToken,
        String role
) { }
