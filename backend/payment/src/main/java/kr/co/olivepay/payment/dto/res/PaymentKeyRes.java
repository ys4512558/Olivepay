package kr.co.olivepay.payment.dto.res;

import lombok.Builder;

@Builder
public record PaymentKeyRes(
        String key
) {

}
