package kr.co.olivepay.transaction.state.account;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentDetailSagaMapper;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import kr.co.olivepay.transaction.state.PaymentState;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AccountBalanceCheckSuccess implements PaymentState {

    /**
     * 최대 3개의 카드에 대한 잔액 조회 성공
     * 실제 결제 요청 이벤트 발행
     *
     * @param paymentSaga
     */
    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        List<PaymentDetailApplyEvent> paymentDetailApplyEventList
                = new ArrayList<>();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentDetailApplyEvent paymentDetailApplyEvent
                    = PaymentDetailSagaMapper.toPaymentDetailApplyEvent(paymentDetailSaga);
            paymentDetailApplyEventList.add(paymentDetailApplyEvent);
        }

        publishPaymentApplyEvent(paymentSaga, paymentDetailApplyEventList);
    }

    /**
     * 잔액 체크 성공 -> 결제 적용 이벤트 발행
     *
     * @param paymentSaga
     * @param paymentDetailApplyEventList
     */
    private static void publishPaymentApplyEvent(PaymentSaga paymentSaga, List<PaymentDetailApplyEvent> paymentDetailApplyEventList) {
        //결제 요청 이벤트로 컨버팅
        PaymentApplyEvent paymentApplyEvent
                = PaymentSagaMapper.toPaymentApplyEvent(paymentSaga, paymentDetailApplyEventList);
        log.info("잔액 체크 성공 -> 결제 적용 이벤트 발행 : [{}]", paymentApplyEvent);
        //결제 요청 이벤트 발행
        paymentSaga.publishEvent(
                Topic.PAYMENT_APPLY,
                paymentSaga.getKey(),
                paymentApplyEvent
        );
    }
}
