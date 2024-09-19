package kr.co.olivepay.auth.dto.req;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginReq (
        @Size(min = 11, max = 11, message = "전화번호는 11자 입니다.")
        String phoneNumber,

        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
                message = "비밀번호 형식이 유효하지 않습니다."
        )
        String password
) { }
