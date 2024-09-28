package kr.co.olivepay.funding.dto.req;

import lombok.Builder;

@Builder
public record FundingCreateReq(
	Long couponUserId,
	Long amount
) {
}
