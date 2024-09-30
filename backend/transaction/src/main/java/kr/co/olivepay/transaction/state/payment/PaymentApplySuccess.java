package kr.co.olivepay.transaction.state.payment;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.state.PaymentState;

import java.util.List;

public class PaymentApplySuccess implements PaymentState {


    /**
     * State : 결제 적용 성공
     * -> 쿠폰 적용 이벤트 발행
     * @param paymentSaga
     */
    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentCardSearchRes paymentCard = paymentDetailSaga.getPaymentCard();
            if (paymentCard.cardType() == CardType.COUPON) {
                //쿠폰이 있다면 쿠폰 적용 이벤트 발행
                CouponApplyEvent couponApplyEvent
                        = CouponApplyEvent.builder()
                                          .couponId(paymentSaga.getCouponId())
                                          .price(paymentDetailSaga.getPrice())
                                          .build();
                paymentSaga.publishEvent(
                        Topic.COUPON_APPLY,
                        paymentSaga.getKey(),
                        couponApplyEvent
                );
                return;
            }
        }
        //쿠폰이 없다면 결제 성공 처리
        paymentSaga.publishEvent(
                Topic.PAYMENT_COMPLETE,
                paymentSaga.getKey(),
                new PaymentCompleteEvent(paymentSaga.getPaymentId())
        );
    }
}
