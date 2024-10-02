package kr.co.olivepay.core.transaction.topic.event.payment.result;

import lombok.Builder;

/**
 * 결제 프로세스 실패에 대한 이벤트
 * @param paymentId
 * @param failReason
 */
@Builder
public record PaymentFailEvent(
        Long paymentId,
        String failReason
) {

}
