package kr.co.olivepay.card.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CardRegisterReq(
        @NotBlank(message = "카드 번호는 공백일 수 없습니다.")
        @Length(min = 16, max = 16, message = "카드 번호는 16자리 입니다.")
        String realCardNumber,
        @NotBlank(message = "유효기간(년)은 공백일 수 없습니다.")
        @Length(min = 2, max = 2, message = "유효기간(년)은 2자리 입니다.")
        @Pattern(regexp = "^[0-9]{2}$", message = "2자리 숫자만 입력 가능합니다.")
        String expirationYear,
        @NotBlank(message = "유효기간(월)은 공백일 수 없습니다.")
        @Length(min = 2, max = 2, message = "유효기간(월)은 2자리 입니다.")
        @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "유효기간(월)은 01에서 12 사이의 두 자리 숫자여야 합니다.")
        String expirationMonth,
        @NotBlank(message = "CVC는 공백일 수 없습니다.")
        @Length(min = 3, max = 3, message = "CVC는 3자리 입니다.")
        String cvc,
        @NotBlank(message = "카드 비밀번호는 공백일 수 없습니다.")
        @Length(min = 2, max = 2, message = "카드 비밀번호는 2자리 입니다.")
        String creditPassword
) {

}
