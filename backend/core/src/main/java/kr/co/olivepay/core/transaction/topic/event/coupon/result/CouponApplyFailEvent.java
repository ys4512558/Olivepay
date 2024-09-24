package kr.co.olivepay.core.transaction.topic.event.coupon.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 적용 실패 이벤트
 */
@Getter
@Builder
@AllArgsConstructor
public class CouponApplyFailEvent {
    
    //쿠폰 적용 실패 원인 (미리 처리된 쿠폰 등)
    private String failReason;
}
