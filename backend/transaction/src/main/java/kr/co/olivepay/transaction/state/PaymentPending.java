package kr.co.olivepay.transaction.state;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceDetailCheckEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;

import java.util.ArrayList;
import java.util.List;

public class PaymentPending implements PaymentState {

    /**
     * State : 결제 대기 상태
     * Publish : 잔액 확인 이벤트 발행
     * @param paymentSaga
     */
    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<AccountBalanceDetailCheckEvent> accountBalanceDetailCheckEventList
                = new ArrayList<>();

        //실제 결제 정보를 담은 카드에 대한 잔액 조회
        for (PaymentDetailSaga paymentDetailSaga : paymentSaga.getPaymentDetailSagaList()) {
            AccountBalanceDetailCheckEvent accountBalanceDetailCheckEvent
                    = AccountBalanceDetailCheckEvent.builder()
                                                    .cardId(paymentDetailSaga.getPaymentCard()
                                                                             .cardId())
                                                    .price(paymentDetailSaga.getPrice())
                                                    .build();
            accountBalanceDetailCheckEventList.add(accountBalanceDetailCheckEvent);
        }
        //계좌 잔액 조회 이벤트 생성
        AccountBalanceCheckEvent accountBalanceCheckEvent
                = AccountBalanceCheckEvent.builder()
                                          .accountBalanceDetailCheckEventList(accountBalanceDetailCheckEventList)
                                          .build();
        //잔액 조회 이벤트 발행
        paymentSaga.publishEvent(
                Topic.ACCOUNT_BALANCE_CHECK,
                paymentSaga.getKey(),
                accountBalanceCheckEvent
        );
    }
}
