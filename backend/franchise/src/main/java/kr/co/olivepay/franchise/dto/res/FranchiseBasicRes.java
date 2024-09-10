package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 조건에 맞는 가맹점 검색을 위한 dto
 * @param id 가맹점 id
 * @param name 상호명
 * @param category 카테고리
 * @param likes 좋아요 개수
 * @param avgStars 평균 별점
 * @param coupon2 2000원 쿠폰 개수
 * @param coupon4 4000원 쿠폰 개수
 */

@Builder
public record FranchiseBasicRes (
	long id,
	String name,
	String category,
	int likes,
	double avgStars,
	int coupon2,
	int coupon4 ){

}
