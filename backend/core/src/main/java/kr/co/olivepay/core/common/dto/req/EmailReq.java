package kr.co.olivepay.core.common.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record EmailReq(
        @NotEmpty(message = "이메일은 필수 요청값입니다.")
        String email,
        @NotEmpty(message = "후원 내역은 필수 요청값입니다.")
        List<CouponUseHistory> histories
) {
}
