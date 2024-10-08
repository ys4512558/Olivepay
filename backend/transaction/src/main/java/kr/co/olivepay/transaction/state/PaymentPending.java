package kr.co.olivepay.transaction.state;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceDetailCheckEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentDetailSagaMapper;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PaymentPending implements PaymentState {

    /**
     * State : 결제 대기 상태
     * Publish : 잔액 확인 이벤트 발행
     *
     * @param paymentSaga
     */
    @Override
    public void operate(PaymentSaga paymentSaga) {
        List<AccountBalanceDetailCheckEvent> accountBalanceDetailCheckEventList
                = new ArrayList<>();

        //실제 결제 정보를 담은 카드에 대한 잔액 조회
        for (PaymentDetailSaga paymentDetailSaga : paymentSaga.getPaymentDetailSagaList()) {
            AccountBalanceDetailCheckEvent accountBalanceDetailCheckEvent
                    = PaymentDetailSagaMapper.toAccountBalanceDetailCheckEvent(paymentDetailSaga);
            accountBalanceDetailCheckEventList.add(accountBalanceDetailCheckEvent);
        }

        //계좌 잔액 조회 이벤트 생성
        AccountBalanceCheckEvent accountBalanceCheckEvent
                = PaymentSagaMapper.toAccountBalanceCheckEvent(paymentSaga, accountBalanceDetailCheckEventList);
        log.info("결제 프로세스 시작 -> 계좌 잔액 체크 이벤트 발행 : [{}]", accountBalanceCheckEvent);
        //잔액 조회 이벤트 발행
        paymentSaga.publishEvent(
                Topic.ACCOUNT_BALANCE_CHECK,
                paymentSaga.getKey(),
                accountBalanceCheckEvent
        );
    }
}
