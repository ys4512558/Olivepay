package kr.co.olivepay.funding.dto.res;

import lombok.Builder;

@Builder
public record CouponTransferStateRes(
        Boolean isSuccess,
        String failReason
) {

}
