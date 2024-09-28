package kr.co.olivepay.transaction.state.account;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.state.PaymentState;

import java.util.ArrayList;
import java.util.List;

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
            PaymentCardSearchRes paymentCard = paymentDetailSaga.getPaymentCard();
            PaymentDetailApplyEvent paymentDetailApplyEvent
                    = PaymentDetailApplyEvent.builder()
                                             .price(paymentDetailSaga.getPrice())
                                             .paymentCard(paymentCard)
                                             .build();
            paymentDetailApplyEventList.add(paymentDetailApplyEvent);
        }

        PaymentApplyEvent paymentApplyEvent = PaymentApplyEvent.builder()
                                                               .memberId(paymentSaga.getMemberId())
                                                               .userKey(paymentSaga.getUserKey())
                                                               .franchiseId(paymentSaga.getFranchiseId())
                                                               .paymentDetailApplyEventList(paymentDetailApplyEventList)
                                                               .build();

        //결제 요청 이벤트 발행
        paymentSaga.publishEvent(
                Topic.PAYMENT_APPLY,
                paymentSaga.getKey(),
                paymentApplyEvent
        );
    }
}
