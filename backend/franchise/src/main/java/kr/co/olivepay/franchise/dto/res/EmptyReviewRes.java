package kr.co.olivepay.franchise.dto.res;

import java.time.LocalDateTime;

import lombok.Builder;

/**
 * 작성하지 않은 리뷰 dto
 * @param reviewId 리뷰 id
 * @param franchise 가맹점(가맹점 id, 상호명)
 * @param createdAt 결제 시각
 */
@Builder
public record EmptyReviewRes(
	Long reviewId,
	FranchiseMinimalRes franchise,
	LocalDateTime createdAt
) {
}
