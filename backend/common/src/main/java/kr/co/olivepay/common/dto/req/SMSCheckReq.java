package kr.co.olivepay.common.dto.req;

import jakarta.validation.constraints.Size;

public record SMSCheckReq(
        @Size(min = 11, max = 11, message = "전화번호는 11자리로 입력해야합니다.")
        String phone,
        @Size(min = 6, max = 6, message = "인증번호는 6자리로 입력해야합니다.")
        String code
) {
}
