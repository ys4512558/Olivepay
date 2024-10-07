package kr.co.olivepay.payment.dto.res;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record PaymentMinimalRes(
	Long paymentId,
	Long franchiseId,
	LocalDateTime createdAt
){
}
