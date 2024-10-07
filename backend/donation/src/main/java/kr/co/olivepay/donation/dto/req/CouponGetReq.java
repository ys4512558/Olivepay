package kr.co.olivepay.donation.dto.req;

import jakarta.validation.constraints.NotNull;
import kr.co.olivepay.donation.validation.ValidCouponUnit;

public record CouponGetReq(
        @ValidCouponUnit
        String couponUnit,
        @NotNull
        Long franchiseId
) {
}
