package kr.co.olivepay.member.dto.res;

import lombok.Builder;

@Builder
public record UserInfoRes(
        Long memberId,
        String name,
        String phoneNumber,
        String nickname,
        String role
) { }
