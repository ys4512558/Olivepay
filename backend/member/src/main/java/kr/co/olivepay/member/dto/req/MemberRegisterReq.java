package kr.co.olivepay.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberRegisterReq (
        @Size(min = 3, max = 30, message = "이름은 최소 3자, 최대 30자 입니다.")
        String name,

        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
                message = "비밀번호 형식이 유효하지 않습니다."
        )
        String password,

        @Size(min = 11, max = 11, message = "전화번호는 11자 입니다.")
        String phoneNumber
) { }
