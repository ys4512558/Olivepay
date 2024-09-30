package kr.co.olivepay.transaction.state.account;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.state.PaymentState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountBalanceCheckFail implements PaymentState {

    private String failReason;

    @Override
    public void operate(PaymentSaga paymentSaga) {
        PaymentFailEvent paymentFailEvent = PaymentFailEvent.builder()
                                                 .paymentId(paymentSaga.getPaymentId())
                                                 .memberId(paymentSaga.getMemberId())
                                                 .failReason(failReason)
                                                 .build();
        paymentSaga.publishEvent(
                Topic.PAYMENT_FAIL,
                paymentSaga.getKey(),
                paymentFailEvent
        );
    }
}
