package kr.co.olivepay.auth.dto.res;

import lombok.Builder;

@Builder
public record OwnerLoginRes (
        String accessToken,
        String refreshToken,
        String role,
        Long franchiseId
) { }
