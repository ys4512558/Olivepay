package kr.co.olivepay.core.transaction.topic.event.coupon;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 적용 요청 이벤트
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponApplyEvent {

    //쿠폰 ID
    Long couponId;
    //사용된 금액
    Long price;
}
