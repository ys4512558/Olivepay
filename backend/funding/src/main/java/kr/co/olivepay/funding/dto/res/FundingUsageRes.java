package kr.co.olivepay.funding.dto.res;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record FundingUsageRes(
	String organization,
	Long amount,
	LocalDateTime createdAt
) {
}
