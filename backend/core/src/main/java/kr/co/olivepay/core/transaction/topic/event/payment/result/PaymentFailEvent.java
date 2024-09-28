package kr.co.olivepay.core.transaction.topic.event.payment.result;

import lombok.Builder;

@Builder
public record PaymentFailEvent(
        Long paymentId,
        Long memberId,
        String failReason
) {

}
