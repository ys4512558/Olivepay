package kr.co.olivepay.franchise.dto.res;

import java.util.List;

import lombok.Builder;

/**
 * 특점 가맹점에 대한 리뷰와 페이징 처리를 위한 index를 담은 dto
 * @param nextIndex
 * @param reviews
 */
@Builder
public record PagedUserReviewsRes(
	Long nextIndex,
	List<UserReviewRes> reviews
) {
}
