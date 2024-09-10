package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 가맹점 상세 검색 시 사용할 리뷰 dto
 * @param id 리뷰 id
 * @param userId 사용자 id
 * @param userName 사용자 이름
 * @param stars 별점
 * @param content 내용
 */
@Builder
public record UserReviewRes(
	long id,
	long userId,
	String userName,
	int stars,
	String content
) {

}
