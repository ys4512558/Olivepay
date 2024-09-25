package kr.co.olivepay.franchise.dto.res;

import java.util.List;

import lombok.Builder;

/**
 * 내가 작성한 리뷰와 페이징 처리를 위한 index를 담은 dto
 * @param nextIndex
 * @param reviews
 */
@Builder
public record PagedFranchiseReviewsRes(
	Long nextIndex,
	List<FranchiseReviewRes> reviews
) {
}
