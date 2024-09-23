package kr.co.olivepay.donation.dto.req;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record DonationReq(
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "이메일 형식이 알맞지 않습니다."
        )
        String email,

        @Size(min = 11, max = 11, message = "전화번호는 11자이어야만 합니다.")
        String phoneNumber,

        @Positive(message = "금액은 양수인 정수여야 합니다.")
        Integer money,

        Long franchiseId,

        @NotBlank(message = "메시지는 필수입니다.")
        String message,

        @Min(value = 0, message = "쿠폰 발급 갯수는 0 이상이어야 합니다. (4000원권)")
        Long coupon4,

        @Min(value = 0, message = "쿠폰 발급 갯수는 0 이상이어야 합니다. (2000원권)")
        Long coupon2,

        @NotBlank(message = "계좌 번호는 공백일 수 없습니다.")
        String accountNumber
) {
}
