package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import kr.co.olivepay.transaction.state.PaymentState;

public class CouponTransferSuccess implements PaymentState {

    @Override
    public void operate(PaymentSaga paymentSaga) {
        publishCouponUsedEvent(paymentSaga);
    }

    /**
     * 쿠폰 사용처리 이벤트 발행
     *
     * @param paymentSaga
     */
    private static void publishCouponUsedEvent(PaymentSaga paymentSaga) {
        CouponUsedEvent couponUsedEvent
                = PaymentSagaMapper.toCouponUsedEvent(paymentSaga);
        paymentSaga.publishEvent(
                Topic.COUPON_USED,
                paymentSaga.getKey(),
                couponUsedEvent
        );
    }
}
