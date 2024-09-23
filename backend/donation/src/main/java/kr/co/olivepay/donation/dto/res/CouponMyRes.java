package kr.co.olivepay.donation.dto.res;

import lombok.Builder;

@Builder
public record CouponMyRes(
        Long franchiseId,
        String franchiseName,
        String couponUnit,
        String message
) {
}
