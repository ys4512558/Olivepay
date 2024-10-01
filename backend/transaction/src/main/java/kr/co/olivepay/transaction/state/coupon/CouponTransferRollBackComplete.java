package kr.co.olivepay.transaction.state.coupon;

import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplyFailEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.mapper.PaymentDetailSagaMapper;
import kr.co.olivepay.transaction.mapper.PaymentSagaMapper;
import kr.co.olivepay.transaction.state.PaymentState;

import java.util.ArrayList;
import java.util.List;

/**
 * 쿠폰 이체 롤백 완료
 * -> 결제 적용 롤백을 위해 적용 실패 이벤트 발행
 */
public class CouponTransferRollBackComplete implements PaymentState {

    @Override
    public void operate(PaymentSaga paymentSaga) {
        publishPaymentApplyFailEvent(paymentSaga);
    }

    private static void publishPaymentApplyFailEvent(PaymentSaga paymentSaga) {
        List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
        List<PaymentApplyHistory> paymentApplyHistoryList = new ArrayList<>();
        for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
            PaymentApplyHistory paymentApplyHistory
                    = PaymentDetailSagaMapper.toPaymentApplyHistory(paymentDetailSaga);
            paymentApplyHistoryList.add(paymentApplyHistory);
        }

        PaymentApplyFailEvent paymentApplyFailEvent
                = PaymentSagaMapper.toPaymentApplyFailEvent(paymentSaga, paymentApplyHistoryList);

        paymentSaga.publishEvent(
                Topic.PAYMENT_APPLY_FAIL,
                paymentSaga.getKey(),
                paymentApplyFailEvent
        );
    }
}
