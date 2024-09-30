package kr.co.olivepay.core.donation.dto.res;

import lombok.Builder;

@Builder
public record CouponRes(
        Long franchiseId,
        Long coupon2,
        Long coupon4
) { }
