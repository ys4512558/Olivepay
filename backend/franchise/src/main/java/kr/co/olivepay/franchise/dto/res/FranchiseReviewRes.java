package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 내가 작성한 리뷰를 검색하는 경우 사용할 리뷰 dto 
 * @param id 리뷰 id
 * @param franchise 가맹점(가맹점 id, 상호명)
 * @param stars 별점
 * @param content 내용
 */

@Builder
public record FranchiseReviewRes(
	Long id,
	FranchiseMinimalRes franchise,
	Integer stars,
	String content
) {



}
