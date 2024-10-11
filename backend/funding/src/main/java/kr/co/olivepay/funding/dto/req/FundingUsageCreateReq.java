package kr.co.olivepay.funding.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record FundingUsageCreateReq(
	@NotNull(message = "기부단체는 필수 입력값입니다.")
	String organization,

	@NotNull(message = "기부금은 필수 입력값입니다.")
	@Positive(message = "기부금은 양수여야 합니다.")
	Long amount
) {
}
