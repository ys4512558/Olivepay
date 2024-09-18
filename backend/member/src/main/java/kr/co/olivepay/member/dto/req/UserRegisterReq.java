package kr.co.olivepay.member.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRegisterReq(
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        @Size(max = 30, message = "닉네임은 최대 30자 입니다.")
        String nickname,

        @NotBlank(message = "생년월일은 공백일 수 없습니다.")
        @Size(min = 8, max = 8, message = "생년월일은 8자 입니다.")
        String birthdate,

        @NotBlank(message = "간편 결제 비밀번호는 공백일 수 없습니다.")
        @Size(min = 6, max = 6, message = "간편 결제 비밀번호는 6자 입니다.")
        String pin,

        @Valid
        MemberRegisterReq memberRegisterReq
) { }
