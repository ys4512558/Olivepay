package kr.co.olivepay.payment.dto.res;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * 가맹점 결제 내역을 위한 dto
 */
@Getter
public class PaymentHistoryRes {
	Long paymentId;
	Long amount;
	LocalDateTime createdAt;
	List<PaymentDetailRes> details;

	@Builder
	public PaymentHistoryRes(Long paymentId, Long amount,
		LocalDateTime createdAt,
		List<PaymentDetailRes> details) {
		this.paymentId = paymentId;
		this.amount = amount;
		this.createdAt = createdAt;
		this.details = details;
	}

}