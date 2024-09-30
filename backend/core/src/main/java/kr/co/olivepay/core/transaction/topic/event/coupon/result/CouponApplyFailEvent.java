package kr.co.olivepay.core.transaction.topic.event.coupon.result;

import lombok.Builder;

/**
 * 쿠폰 적용 실패 이벤트
 */
@Builder
public record CouponApplyFailEvent(
        //쿠폰 적용 실패 원인 (미리 처리된 쿠폰 등)
        String failReason
) {

}
