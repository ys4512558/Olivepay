package kr.co.olivepay.core.transaction.topic.event.coupon.result;

import lombok.Builder;

/**
 * 쿠폰 사용처리 실패 이벤트
 */
@Builder
public record CouponUsedFailEvent(
        //쿠폰 사용 처리 실패 원인 (이미 처리된 쿠폰 등)
        String failReason
) {

}
