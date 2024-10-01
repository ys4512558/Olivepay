package kr.co.olivepay.funding.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record FundingCreateReq(
	@NotNull(message = "쿠폰-유저 ID는 필수 입력값입니다.")
	Long couponUserId,

	@NotNull(message = "기부금은 필수 입력값입니다.")
	@Positive(message = "기부금은 양수여야 합니다.")
	Long amount
) {
}
