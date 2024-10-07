package kr.co.olivepay.donation.dto.res;

import lombok.Builder;

@Builder
public record CouponUsedStateRes(
        Boolean isSuccess,
        String failReason
) {

}
