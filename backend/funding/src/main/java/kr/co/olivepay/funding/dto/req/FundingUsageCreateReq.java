package kr.co.olivepay.funding.dto.req;

import lombok.Builder;

@Builder
public record FundingUsageCreateReq(
	String organization,
	Long amount
) {
}
