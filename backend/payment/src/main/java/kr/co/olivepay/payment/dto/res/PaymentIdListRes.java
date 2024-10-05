package kr.co.olivepay.payment.dto.res;

import java.util.List;

import lombok.Builder;

/**
 * 3일 이내 결제 완료된 결제 내역 ID 리스트를 담는 dto
 * @param paymentIdList
 */
@Builder
public record PaymentIdListRes(
	List<Long> paymentIdList
) {
}
