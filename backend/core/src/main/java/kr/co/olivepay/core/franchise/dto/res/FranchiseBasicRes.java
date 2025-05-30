package kr.co.olivepay.core.franchise.dto.res;

import lombok.Builder;

/**
 * 조건에 맞는 가맹점 검색을 위한 dto
 * @param franchiseId 가맹점 id
 * @param franchiseName 상호명
 * @param latitude 위도
 * @param longitude 경도
 * @param category 카테고리
 * @param likes 좋아요 개수
 * @param avgStars 평균 별점
 * @param coupons 쿠폰 개수
 */

@Builder
public record FranchiseBasicRes(
	Long franchiseId,
	String franchiseName,
	Double latitude,
	Double longitude,
	String category,
	Integer likes,
	Integer coupons,
	Double avgStars){

}
