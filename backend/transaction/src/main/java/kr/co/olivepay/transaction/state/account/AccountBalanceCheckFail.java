package kr.co.olivepay.transaction.state.account;

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
public class AccountBalanceCheckFail implements PaymentState {

    private String failReason;

    /**
     * 실패 시 transactionUniqueNo != null인 롤백 이벤트를 결제 취소 요청하도록
     * @param paymentSaga
     */
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

        paymentSaga.publishEvent(
                Topic.PAYMENT_FAIL,
                paymentSaga.getKey(),
                paymentFailEvent
        );
    }
}
