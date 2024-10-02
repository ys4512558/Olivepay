package kr.co.olivepay.core.transaction.topic.event.coupon;


import lombok.Builder;

/**
 * 쿠폰 사용 처리 요청 이벤트
 *
 * @param memberId
 * @param couponUserId
 */
@Builder
public record CouponUsedEvent(
        //멤버 ID
        Long memberId,
        //쿠폰 ID
        Long couponUserId
) {

}
