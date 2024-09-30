package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.state.PaymentState;

public class CouponUsedSuccess implements PaymentState {

    @Override
    public void operate(PaymentSaga paymentSaga) {
        publishPaymentCompleteEvent(paymentSaga);
    }

    /**
     * 결제 프로세스 성공 이벤트 발행
     *
     * @param paymentSaga
     */
    private void publishPaymentCompleteEvent(PaymentSaga paymentSaga) {
        paymentSaga.publishEvent(
                Topic.PAYMENT_COMPLETE,
                paymentSaga.getKey(),
                new PaymentCompleteEvent(paymentSaga.getPaymentId())
        );
    }
}
