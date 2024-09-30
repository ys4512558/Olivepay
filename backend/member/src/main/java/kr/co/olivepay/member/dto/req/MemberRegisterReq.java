package kr.co.olivepay.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberRegisterReq (
        @Size(min = 2, max = 30, message = "이름은 최소 2자, 최대 30자 입니다.")
        String name,

        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
                message = "비밀번호 형식이 유효하지 않습니다."
        )
        String password,

        @Pattern(
                regexp = "^\\d{11}$",
                message = "전화번호는 11자리의 숫자여야 합니다."
        )
        String phoneNumber
) { }
