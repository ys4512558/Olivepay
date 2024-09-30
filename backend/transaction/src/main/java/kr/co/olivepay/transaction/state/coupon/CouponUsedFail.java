package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplyFailEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentDetailSagaMapper;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import kr.co.olivepay.transaction.state.PaymentState;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CouponUsedFail implements PaymentState {

    private String failReason;

    /**
     * 쿠폰 사용 처리 실패 시
     * 1. 쿠폰 잔액 이체 취소
     * 2. 결제 취소
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
                Long couponPrice = paymentSaga.getCouponPrice();
                Long differencePrice = couponPrice - price;

                //쿠폰과 결제 금액 차이가 없으면 결제 실패 (롤백 이벤트 발행을 위해)
                if (differencePrice == 0) {
                    publishPaymentApplyFailEvent(paymentSaga);
                }
                //쿠폰 잔액 이체 롤백 이벤트 발행
                publishCouponTransferRollbackEvent(paymentSaga, differencePrice);
                return;
            }
        }
    }

    /**
     * 결제 적용 실패 이벤트 발행
     *
     * @param paymentSaga
     */
    private void publishPaymentApplyFailEvent(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        List<PaymentApplyHistory> paymentApplyHistoryList = new ArrayList<>();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentApplyHistory paymentApplyHistory
                    = PaymentDetailSagaMapper.toPaymentApplyHistory(paymentDetailSaga);
            paymentApplyHistoryList.add(paymentApplyHistory);
        }

        PaymentApplyFailEvent paymentApplyFailEvent
                = PaymentSagaMapper.toPaymentApplyFailEvent(paymentSaga, failReason, paymentApplyHistoryList);

        paymentSaga.publishEvent(
                Topic.PAYMENT_APPLY_FAIL,
                paymentSaga.getKey(),
                paymentApplyFailEvent
        );
    }

    /**
     * 쿠폰 잔액 이체 롤백 이벤트 발행
     *
     * @param paymentSaga
     * @param differencePrice
     */
    private void publishCouponTransferRollbackEvent(PaymentSaga paymentSaga, Long differencePrice) {
        CouponTransferRollBackEvent couponTransferRollbackEvent
                = PaymentSagaMapper.toCouponTransferRollbackEvent(paymentSaga, differencePrice);

        paymentSaga.publishEvent(
                Topic.COUPON_TRANSFER_ROLLBACK,
                paymentSaga.getKey(),
                couponTransferRollbackEvent
        );
    }
}
