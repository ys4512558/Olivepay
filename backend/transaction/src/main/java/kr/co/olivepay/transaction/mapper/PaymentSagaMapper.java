package kr.co.olivepay.transaction.mapper;

import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceDetailCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollbackEvent;
import kr.co.olivepay.transaction.PaymentSaga;

import java.util.List;

public class PaymentSagaMapper {

    /**
     * 잔액 체크 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @param accountBalanceDetailCheckEventList
     * @return {@link AccountBalanceCheckEvent}
     */
    public static AccountBalanceCheckEvent toAccountBalanceCheckEvent(
            PaymentSaga paymentSaga,
            List<AccountBalanceDetailCheckEvent> accountBalanceDetailCheckEventList
    ) {
        return AccountBalanceCheckEvent.builder()
                                       .memberId(paymentSaga.getMemberId())
                                       .userKey(paymentSaga.getUserKey())
                                       .accountBalanceDetailCheckEventList(accountBalanceDetailCheckEventList)
                                       .build();
    }

    /**
     * 결제 적용 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @param paymentDetailApplyEventList
     * @return {@link PaymentApplyEvent}
     */
    public static PaymentApplyEvent toPaymentApplyEvent(
            PaymentSaga paymentSaga,
            List<PaymentDetailApplyEvent> paymentDetailApplyEventList
    ) {
        return PaymentApplyEvent.builder()
                                .memberId(paymentSaga.getMemberId())
                                .userKey(paymentSaga.getUserKey())
                                .franchiseId(paymentSaga.getFranchiseId())
                                .paymentDetailApplyEventList(paymentDetailApplyEventList)
                                .build();
    }

    /**
     * 결제 실패 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @param failReason
     * @return {@link PaymentFailEvent}
     */
    public static PaymentFailEvent toPaymentFailEvent(
            PaymentSaga paymentSaga,
            String failReason,
            List<PaymentRollbackEvent> paymentRollbackEventList) {
        return PaymentFailEvent.builder()
                               .paymentId(paymentSaga.getPaymentId())
                               .userKey(paymentSaga.getUserKey())
                               .memberId(paymentSaga.getMemberId())
                               .failReason(failReason)
                               .paymentRollbackEventList(paymentRollbackEventList)
                               .build();
    }
}
