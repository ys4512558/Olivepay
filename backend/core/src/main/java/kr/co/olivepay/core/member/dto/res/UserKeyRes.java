package kr.co.olivepay.core.member.dto.res;

import lombok.Builder;

@Builder
public record UserKeyRes(
        String userKey
) {

}
