package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackDetailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentDetailSagaMapper;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import kr.co.olivepay.transaction.state.PaymentState;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CouponTransferFail implements PaymentState {

    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        List<PaymentRollBackDetailEvent> paymentRollBackDetailEventList = new ArrayList<>();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentRollBackDetailEvent paymentRollbackDetailEvent
                    = PaymentDetailSagaMapper.toPaymentRollbackDetailEvent(paymentDetailSaga);
            paymentRollBackDetailEventList.add(paymentRollbackDetailEvent);
        }

        publishPaymentRollBackEvent(paymentSaga, paymentRollBackDetailEventList);
    }

    /**
     * 결제 적용 실패 -> 결제 롤백 이벤트 발행
     *
     * @param paymentSaga
     * @param paymentRollBackDetailEventList
     */
    private void publishPaymentRollBackEvent(
            PaymentSaga paymentSaga,
            List<PaymentRollBackDetailEvent> paymentRollBackDetailEventList
    ) {
        PaymentRollBackEvent paymentRollBackEvent
                = PaymentSagaMapper.toPaymentRollBackEvent(paymentSaga, paymentRollBackDetailEventList);

        //payment 서비스로 결제 프로세스 실패 이벤트 발행
        paymentSaga.publishEvent(
                Topic.PAYMENT_APPLY_ROLLBACK,
                paymentSaga.getKey(),
                paymentRollBackEvent
        );
    }
}
