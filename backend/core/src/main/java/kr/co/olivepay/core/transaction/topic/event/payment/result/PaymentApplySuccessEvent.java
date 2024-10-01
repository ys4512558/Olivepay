package kr.co.olivepay.core.transaction.topic.event.payment.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 결제 적용 성공 이벤트
 */
@Builder
public record PaymentApplySuccessEvent(

) {

}
