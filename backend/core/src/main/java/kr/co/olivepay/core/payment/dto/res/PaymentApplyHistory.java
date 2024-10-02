package kr.co.olivepay.core.payment.dto.res;

import lombok.Builder;

@Builder
public record PaymentApplyHistory(
        String transactionUniqueNo,
        Long paymentDetailId
) {

}
