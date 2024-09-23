package kr.co.olivepay.donation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DonationMyReq(
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        String email,

        @NotBlank(message = "전화번호는 공백일 수 없습니다.")
        String phoneNumber
) {
}
