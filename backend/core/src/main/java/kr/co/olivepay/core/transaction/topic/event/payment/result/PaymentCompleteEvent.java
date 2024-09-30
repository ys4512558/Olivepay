package kr.co.olivepay.core.transaction.topic.event.payment.result;

import lombok.Builder;

/**
 * 결제 프로세스 성공에 대한 이벤트
 * @param paymentId
 */
@Builder
public record PaymentCompleteEvent(
        Long paymentId
) {

}
