package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollbackEvent;
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

    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        List<PaymentRollbackEvent> paymentRollbackEventList = new ArrayList<>();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentRollbackEvent paymentRollbackEvent
                    = PaymentDetailSagaMapper.toPaymentRollbackEvent(paymentDetailSaga);
            paymentRollbackEventList.add(paymentRollbackEvent);
        }

        PaymentFailEvent paymentFailEvent
                = PaymentSagaMapper.toPaymentFailEvent(paymentSaga, failReason, paymentRollbackEventList);

        //payment 서비스로 이벤트 발행
        paymentSaga.publishEvent(
                Topic.PAYMENT_FAIL,
                paymentSaga.getKey(),
                paymentFailEvent
        );
    }
}
