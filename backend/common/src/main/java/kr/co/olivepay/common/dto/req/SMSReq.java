package kr.co.olivepay.common.dto.req;

import jakarta.validation.constraints.Size;

public record SMSReq(
        @Size(min = 11, max = 11, message = "전화번호는 11자리로 입력해야합니다.")
        String phone
) {
}
