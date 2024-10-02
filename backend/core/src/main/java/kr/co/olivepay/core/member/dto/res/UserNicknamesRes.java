package kr.co.olivepay.core.member.dto.res;

import lombok.Builder;

import java.util.List;

@Builder
public record UserNicknamesRes(
        List<UserNicknameRes> members
) { }
