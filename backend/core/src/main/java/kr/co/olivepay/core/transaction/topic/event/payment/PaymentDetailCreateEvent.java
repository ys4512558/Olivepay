package kr.co.olivepay.core.transaction.topic.event.payment;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import lombok.Builder;

/**
 * 결제 프로세스 생성 이벤트
 */
@Builder
public record PaymentDetailCreateEvent(
    //결제 상세정보 ID
    Long paymentDetailId,
    //결제에 사용될 카드 상세 정보
    PaymentCardSearchRes paymentCard,
    //해당 카드로 결제할 금액
    Long price
) {

}
