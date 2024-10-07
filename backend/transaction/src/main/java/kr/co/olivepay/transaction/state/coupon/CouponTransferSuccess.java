package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import kr.co.olivepay.transaction.state.PaymentState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("잔액 이체 성공 -> 쿠폰 사용 처리 이벤트 발행 : [{}]", couponUsedEvent);
        paymentSaga.publishEvent(
                Topic.COUPON_USED,
                paymentSaga.getKey(),
                couponUsedEvent
        );
    }
}
