package kr.co.olivepay.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kr.co.olivepay.member.dto.validation.ValidBirthdate;
import lombok.Builder;

@Builder
public record UserRegisterReq(
        @Schema(example = "홍길동의 별명")
        @Size(min = 2, max = 30, message = "닉네임은 최소 2자, 최대 30자 입니다.")
        String nickname,

        @Schema(example = "20080808")
        @ValidBirthdate
        String birthdate,

        @Schema(example = "001122")
        @Pattern(regexp = "^\\d{6}$", message = "간편 결제 비밀번호는 숫자 6자여야 합니다.")
        String pin,

        @Valid
        MemberRegisterReq memberRegisterReq
) { }
