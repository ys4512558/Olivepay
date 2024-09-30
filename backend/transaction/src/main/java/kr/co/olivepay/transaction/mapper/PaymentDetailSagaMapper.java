package kr.co.olivepay.transaction.mapper;


import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceDetailCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollbackDetailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollbackEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;

public class PaymentDetailSagaMapper {

    /**
     * 잔액 체크 상세 이벤트로 컨버팅
     *
     * @param paymentDetailSaga
     * @return {@link AccountBalanceDetailCheckEvent}
     */
    public static AccountBalanceDetailCheckEvent toAccountBalanceDetailCheckEvent(PaymentDetailSaga paymentDetailSaga) {
        return AccountBalanceDetailCheckEvent.builder()
                                             .cardId(paymentDetailSaga.getPaymentCard()
                                                                      .cardId())
                                             .price(paymentDetailSaga.getPrice())
                                             .build();
    }

    /**
     * 결제 적용 상세 이벤트로 컨버팅
     *
     * @param paymentDetailSaga
     * @return {@link PaymentDetailApplyEvent}
     */
    public static PaymentDetailApplyEvent toPaymentDetailApplyEvent(PaymentDetailSaga paymentDetailSaga) {
        return PaymentDetailApplyEvent.builder()
                                      .price(paymentDetailSaga.getPrice())
                                      .paymentCard(paymentDetailSaga.getPaymentCard())
                                      .build();
    }


    /**
     * 결제 롤백 이벤트로 컨버팅
     *
     * @param paymentDetailSaga
     * @return {@link PaymentRollbackDetailEvent}
     */
    public static PaymentRollbackDetailEvent toPaymentRollbackDetailEvent(PaymentDetailSaga paymentDetailSaga) {
        return PaymentRollbackDetailEvent.builder()
                                         .paymentDetailId(paymentDetailSaga.getPaymentDetailId())
                                         .transactionUniqueNo(paymentDetailSaga.getTransactionUniqueNo())
                                         .paymentCard(paymentDetailSaga.getPaymentCard())
                                         .build();

    }
}
