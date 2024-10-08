package kr.co.olivepay.auth.dto.res;

import lombok.Builder;

@Builder
public record PaymentTokenRes (String paymentToken){
}
