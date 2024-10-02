package kr.co.olivepay.core.transaction.topic.event.coupon;

import lombok.Builder;

/**
 * 해당 쿠폰에 대해 사용 후 남은 잔액 이체 이벤트
 * @param couponUserId
 * @param change
 */
@Builder
public record CouponTransferEvent(
        //쿠폰-유저 ID
        Long couponUserId,
        //쿠폰 사용 후 남은 잔액
        Long change
) {

}
