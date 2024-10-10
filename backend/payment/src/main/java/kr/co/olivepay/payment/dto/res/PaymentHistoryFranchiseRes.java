package kr.co.olivepay.payment.dto.res;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 유저 결제 내역을 위한 dto
 */
@Builder
public record PaymentHistoryFranchiseRes(
		Long paymentId,
		Long amount,
		LocalDateTime createdAt,
		List<PaymentDetailRes> details,
		Long franchiseId,
		String franchiseName
) {
}