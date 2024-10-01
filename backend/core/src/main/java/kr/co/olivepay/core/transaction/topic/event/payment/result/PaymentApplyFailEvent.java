package kr.co.olivepay.core.transaction.topic.event.payment.result;

import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import lombok.Builder;

import java.util.List;

/**
 * 결제 적용 실패 이벤트
 */
@Builder
public record PaymentApplyFailEvent(
        //계좌 잔액 실패 원인 (잔액 부족 등)
        String failReason,
        List<PaymentApplyHistory> paymentApplyHistoryList
) {

}
