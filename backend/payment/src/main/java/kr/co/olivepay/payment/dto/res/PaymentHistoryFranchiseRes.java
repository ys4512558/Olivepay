package kr.co.olivepay.payment.dto.res;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 유저 결제 내역을 위한 dto
 */
@Getter
@SuperBuilder
public class PaymentHistoryFranchiseRes extends PaymentHistoryRes {
	private final Long franchiseId;
	private final String franchiseName;

}