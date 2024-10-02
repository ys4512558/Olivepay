package kr.co.olivepay.core.transaction.topic.event.payment;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import lombok.Builder;

/**
 * 실제 결제 요청을 위한 정보
 */
@Builder
public record PaymentDetailApplyEvent(
        //결제에 사용될 카드 정보
        PaymentCardSearchRes paymentCard,
        //결제될 금액
        Long price
) {


}
