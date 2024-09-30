package kr.co.olivepay.transaction.state.account;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.state.PaymentState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountBalanceCheckFail implements PaymentState {

    private String failReason;

    /**
     * 결제 프로세스 실패
     *
     * @param paymentSaga
     */
    @Override
    public void operate(PaymentSaga paymentSaga) {
        publishPaymentFailEvent(paymentSaga);
    }

    /**
     * 결제 프로세스 실패 이벤트 발행
     *
     * @param paymentSaga
     */
    private void publishPaymentFailEvent(PaymentSaga paymentSaga) {
        PaymentFailEvent paymentFailEvent
                = PaymentFailEvent.builder()
                                  .paymentId(paymentSaga.getPaymentId())
                                  .failReason(failReason)
                                  .build();
        //payment 서비스로 결제 프로세스 실패 이벤트 발행
        paymentSaga.publishEvent(
                Topic.PAYMENT_FAIL,
                paymentSaga.getKey(),
                paymentFailEvent
        );
    }
}
