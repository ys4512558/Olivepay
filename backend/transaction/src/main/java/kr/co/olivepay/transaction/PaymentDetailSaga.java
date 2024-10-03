package kr.co.olivepay.transaction;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentDetailSaga {
    private Long paymentDetailId;
    private Long price;
    @Setter
    private String transactionUniqueNo;
    private PaymentCardSearchRes paymentCard;

    public static PaymentDetailSaga toPaymentDetailSage(PaymentDetailCreateEvent event) {
        return PaymentDetailSaga.builder()
                                .paymentDetailId(event.paymentDetailId())
                                .price(event.price())
                                .paymentCard(event.paymentCard())
                                .build();
    }
}
