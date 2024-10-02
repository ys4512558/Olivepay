package kr.co.olivepay.payment.dto.res;

import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import lombok.Builder;

import java.util.List;

@Builder
public record PaymentApplyStateRes(
        Boolean isSuccess,
        String failReason,
        List<PaymentApplyHistory> paymentApplyHistoryList
) {

}
