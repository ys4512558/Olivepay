package kr.co.olivepay.payment.dto.res;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * 결제 내역 조회를 클라이언트에게 최종적으로 전달할 때 사용하는 dto
 * @param nextIndex
 * @param history
 */
@Builder
public record PagedPaymentHistoryRes(
	Long nextIndex,
	List<PaymentHistoryRes> history
) {
}
