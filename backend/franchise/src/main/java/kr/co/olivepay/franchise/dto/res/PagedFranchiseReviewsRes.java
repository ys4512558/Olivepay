package kr.co.olivepay.franchise.dto.res;

import java.util.List;

import lombok.Builder;

/**
 *
 */
@Builder
public record PagedFranchiseReviewsRes(
	int nextIndex,
	List<FranchiseReviewRes> reviews
) {
}
