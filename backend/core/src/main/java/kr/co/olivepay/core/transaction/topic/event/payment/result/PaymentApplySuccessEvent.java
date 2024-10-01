package kr.co.olivepay.core.transaction.topic.event.payment.result;

import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import lombok.Builder;

import java.util.List;

/**
 * 결제 적용 성공 이벤트
 */
@Builder
public record PaymentApplySuccessEvent(
        List<PaymentApplyHistory> paymentApplyHistoryList
) {

}
