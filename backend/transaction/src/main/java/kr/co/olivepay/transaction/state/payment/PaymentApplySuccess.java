package kr.co.olivepay.transaction.state.payment;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponTransferEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import kr.co.olivepay.transaction.state.PaymentState;

import java.util.List;

public class PaymentApplySuccess implements PaymentState {


    /**
     * State : 결제 적용 성공
     * -> 쿠폰 적용 이벤트 발행
     *
     * @param paymentSaga
     */
    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentCardSearchRes paymentCard = paymentDetailSaga.getPaymentCard();
            //쿠폰이 있다면 쿠폰 잔액 이체 이벤트 발행
            if (paymentCard.cardType() == CardType.COUPON) {
                //쿠폰으로 결제한 금액
                Long price = paymentDetailSaga.getPrice();
                //쿠폰 금액
                Long couponUnit = paymentSaga.getCouponUnit();
                Long differencePrice = couponUnit - price;

                //쿠폰과 결제 금액 차이가 없으면 쿠폰 사용 처리
                if (differencePrice == 0) {
                    publishCouponUsedEvent(paymentSaga);
                }
                //쿠폰과 결제 금액 차액만큼 이체 이벤트 발행
                publishCouponTransferEvent(paymentSaga, differencePrice);
                return;
            }
        }
        //쿠폰이 없으면 결제 성공 처리
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

    /**
     * 쿠폰 잔액 이체 이벤트 발행
     *
     * @param paymentSaga
     * @param differencePrice
     */
    private void publishCouponTransferEvent(PaymentSaga paymentSaga, Long differencePrice) {
        CouponTransferEvent couponTransferEvent
                = PaymentSagaMapper.toCouponTransferEvent(paymentSaga, differencePrice);
        paymentSaga.publishEvent(
                Topic.COUPON_TRANSFER,
                paymentSaga.getKey(),
                couponTransferEvent
        );
    }

    /**
     * 쿠폰 사용처리 이벤트 발행
     *
     * @param paymentSaga
     */
    private void publishCouponUsedEvent(PaymentSaga paymentSaga) {
        CouponUsedEvent couponUsedEvent
                = PaymentSagaMapper.toCouponUsedEvent(paymentSaga);
        paymentSaga.publishEvent(
                Topic.COUPON_USED,
                paymentSaga.getKey(),
                couponUsedEvent
        );
    }
}
