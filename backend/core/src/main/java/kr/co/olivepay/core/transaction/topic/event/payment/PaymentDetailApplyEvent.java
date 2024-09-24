package kr.co.olivepay.core.transaction.topic.event.payment;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 실제 결제 요청을 위한 정보
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailApplyEvent {

    //결제에 사용될 카드 정보
    private PaymentCardSearchRes paymentCard;
    //결제될 금액
    private Long price;

}
