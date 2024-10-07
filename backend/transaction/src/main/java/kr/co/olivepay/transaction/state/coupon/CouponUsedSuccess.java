package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.state.PaymentState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("쿠폰 사용 처리 성공 -> 결제 완료 이벤트 발행 : [{}]", paymentSaga.toString());
        paymentSaga.publishEvent(
                Topic.PAYMENT_COMPLETE,
                paymentSaga.getKey(),
                new PaymentCompleteEvent(paymentSaga.getPaymentId())
        );
    }
}
