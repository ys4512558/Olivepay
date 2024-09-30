package kr.co.olivepay.transaction.state.payment;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollbackDetailEvent;
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
public class PaymentApplyFail implements PaymentState {

    private String failReason;

    /**
     * 실패 시 transactionUniqueNo != null인 롤백 이벤트를 결제 취소 요청하도록
     * -> Payment_Fail(프로세스 종료)
     *
     * @param paymentSaga
     */
    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        List<PaymentRollbackDetailEvent> paymentRollbackDetailEventList = new ArrayList<>();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentRollbackDetailEvent paymentRollbackDetailEvent
                    = PaymentDetailSagaMapper.toPaymentRollbackDetailEvent(paymentDetailSaga);
            paymentRollbackDetailEventList.add(paymentRollbackDetailEvent);
        }

        publishPaymentRollbackEvent(paymentSaga, paymentRollbackDetailEventList);
    }

    /**
     * 결제 적용 실패 -> 결제 롤백 이벤트 발행
     *
     * @param paymentSaga
     * @param paymentRollbackDetailEventList
     */
    private void publishPaymentRollbackEvent(PaymentSaga paymentSaga, List<PaymentRollbackDetailEvent> paymentRollbackDetailEventList) {
        PaymentRollbackEvent paymentRollbackEvent
                = PaymentSagaMapper.toPaymentRollbackEvent(paymentSaga, failReason, paymentRollbackDetailEventList);

        //payment 서비스로 결제 프로세스 실패 이벤트 발행
        paymentSaga.publishEvent(
                Topic.PAYMENT_APPLY_ROLLBACK,
                paymentSaga.getKey(),
                paymentRollbackEvent
        );
    }
}
