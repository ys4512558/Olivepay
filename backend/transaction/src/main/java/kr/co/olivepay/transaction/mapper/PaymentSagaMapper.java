package kr.co.olivepay.transaction.mapper;

import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceDetailCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponTransferEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplyFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackDetailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackEvent;
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
     * 결제 롤백 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @return {@link PaymentRollBackEvent}
     */
    public static PaymentRollBackEvent toPaymentRollBackEvent(
            PaymentSaga paymentSaga,
            List<PaymentRollBackDetailEvent> paymentRollBackDetailEventList) {
        return PaymentRollBackEvent.builder()
                                   .paymentId(paymentSaga.getPaymentId())
                                   .userKey(paymentSaga.getUserKey())
                                   .memberId(paymentSaga.getMemberId())
                                   .failReason(paymentSaga.getFailReason())
                                   .paymentRollBackDetailEventList(paymentRollBackDetailEventList)
                                   .build();
    }

    /**
     * 결제 적용 실패 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @param paymentApplyHistoryList
     * @return {@link PaymentApplyFailEvent}
     */
    public static PaymentApplyFailEvent toPaymentApplyFailEvent(
            PaymentSaga paymentSaga,
            List<PaymentApplyHistory> paymentApplyHistoryList
    ) {
        return PaymentApplyFailEvent.builder()
                                    .failReason(paymentSaga.getFailReason())
                                    .paymentApplyHistoryList(paymentApplyHistoryList)
                                    .build();
    }

    /**
     * 쿠폰 잔액 이체 롤백 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @param change
     * @return {@link CouponTransferRollBackEvent}
     */
    public static CouponTransferRollBackEvent toCouponTransferRollBackEvent(
            PaymentSaga paymentSaga,
            Long change
    ) {
        return CouponTransferRollBackEvent.builder()
                                          .couponUserId(paymentSaga.getCouponUserId())
                                          .change(change)
                                          .build();
    }

    /**
     * 결제 프로세스 실패 이벤트 컨버팅
     *
     * @param paymentSaga
     * @return {@link PaymentFailEvent}
     */
    public static PaymentFailEvent toPaymentFailEvent(
            PaymentSaga paymentSaga
    ) {
        return PaymentFailEvent.builder()
                               .paymentId(paymentSaga.getPaymentId())
                               .failReason(paymentSaga.getFailReason())
                               .build();
    }

    /**
     * 쿠폰 잔액 이체 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @param differencePrice
     * @return
     */
    public static CouponTransferEvent toCouponTransferEvent(PaymentSaga paymentSaga, Long differencePrice) {
        return CouponTransferEvent.builder()
                                  .couponUserId(paymentSaga.getCouponUserId())
                                  .change(differencePrice)
                                  .build();
    }

    /**
     * 쿠폰 사용 처리 이벤트로 컨버팅
     *
     * @param paymentSaga
     * @return
     */
    public static CouponUsedEvent toCouponUsedEvent(PaymentSaga paymentSaga) {
        return CouponUsedEvent.builder()
                              .couponUserId(paymentSaga.getCouponUserId())
                              .build();
    }
}
