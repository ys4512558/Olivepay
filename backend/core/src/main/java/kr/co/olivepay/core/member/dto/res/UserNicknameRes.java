package kr.co.olivepay.core.member.dto.res;

import lombok.Builder;

@Builder
public record UserNicknameRes (
        Long memberId,
        String nickname
){ }
