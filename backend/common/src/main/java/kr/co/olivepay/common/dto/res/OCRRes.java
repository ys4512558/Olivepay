package kr.co.olivepay.common.dto.res;

import lombok.Builder;

@Builder
public record OCRRes(

        String cardNumber,
        String expirationMonth,
        String expirationYear
) {
}
