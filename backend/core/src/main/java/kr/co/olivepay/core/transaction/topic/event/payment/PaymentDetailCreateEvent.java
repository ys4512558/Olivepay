package kr.co.olivepay.core.transaction.topic.event.payment;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 프로세스 생성 이벤트
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailCreateEvent {

    //결제 상세정보 ID
    private Long paymentDetailId;
    //결제에 사용될 카드 상세 정보
    private PaymentCardSearchRes paymentCard;
    //해당 카드로 결제할 금액
    private Long price;

}
