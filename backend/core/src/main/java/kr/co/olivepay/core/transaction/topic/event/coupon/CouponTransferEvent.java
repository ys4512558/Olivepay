package kr.co.olivepay.core.transaction.topic.event.coupon;

import lombok.Builder;

@Builder
public record CouponTransferEvent(
        Long transferPrice
) {

}
