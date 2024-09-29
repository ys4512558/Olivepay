package kr.co.olivepay.core.transaction.topic.event.payment.result;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import lombok.Builder;

/**
 * 결제 번호, 카드 정보를 통해 결제 취소 요청
 *
 * @param transactionUniqueNo
 * @param paymentCard
 */
@Builder
public record PaymentRollbackEvent(
        Long paymentDetailId,
        String transactionUniqueNo,
        PaymentCardSearchRes paymentCard
) {

}
