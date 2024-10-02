package kr.co.olivepay.core.transaction.topic.event.coupon.result;

import lombok.Builder;

/**
 * 사용 후 남은 잔액 이체 롤백 이벤트
 * @param couponUserId
 * @param change
 */
@Builder
public record CouponTransferRollbackEvent(
        //쿠폰-유저 ID
        Long couponUserId,
        //쿠폰 사용 후 남은 잔액
        Long change
) {
}
