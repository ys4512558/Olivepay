package kr.co.olivepay.member.dto.req;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserInfoChangeReq(
        @Size(min = 2, max = 30, message = "닉네임은 최소 2자, 최대 30자 입니다.")
        String nickname
) { }
