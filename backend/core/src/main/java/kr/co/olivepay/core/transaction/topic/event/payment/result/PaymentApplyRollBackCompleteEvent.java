package kr.co.olivepay.core.transaction.topic.event.payment.result;

import lombok.Builder;

@Builder
public record PaymentApplyRollBackCompleteEvent(
        Long paymentId
) {
}
