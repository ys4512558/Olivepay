package kr.co.olivepay.transaction;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentDetailSaga {
    private Long paymentDetailId;
    private Long price;
    private PaymentCardSearchRes paymentCard;

    public static PaymentDetailSaga toPaymentDetailSage(PaymentDetailCreateEvent event) {
        return PaymentDetailSaga.builder()
                                .paymentDetailId(event.getPaymentDetailId())
                                .price(event.getPrice())
                                .paymentCard(event.getPaymentCard())
                                .build();
    }
}
