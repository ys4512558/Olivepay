package kr.co.olivepay.core.member.dto.req;

import jakarta.validation.constraints.Pattern;

public record UserPinCheckReq(
        @Pattern(regexp = "^\\d{6}$", message = "간편 결제 비밀번호는 숫자 6자여야 합니다.")
        String pin
) { }
