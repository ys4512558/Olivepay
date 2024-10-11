package kr.co.olivepay.payment.dto.res;

import kr.co.olivepay.payment.entity.enums.PaymentType;
import lombok.Builder;

/**
 * 결제 상세 정보를 담기 위한 dto
 * @param type
 * @param name
 * @param amount
 */
@Builder
public record PaymentDetailRes(
	PaymentType type,
	String name,
	Long amount
) {
}