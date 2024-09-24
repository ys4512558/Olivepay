package kr.co.olivepay.core.transaction.topic.event.payment.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 적용 실패 이벤트
 */
@Builder
public record PaymentApplyFailEvent(
    //계좌 잔액 실패 원인 (잔액 부족 등)
    String failReason
) {

}
