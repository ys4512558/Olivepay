package kr.co.olivepay.core.transaction.topic.event.payment.result;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import lombok.Builder;

@Builder
public record PaymentRollbackDetailEvent(
        Long paymentDetailId,
        String transactionUniqueNo,
        PaymentCardSearchRes paymentCard
) {
}
