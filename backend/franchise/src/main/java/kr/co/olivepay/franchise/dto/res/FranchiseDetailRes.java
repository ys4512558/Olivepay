package kr.co.olivepay.franchise.dto.res;

import java.util.List;

import lombok.Builder;

/**
 * 가맹점 상세 검색을 위한 dto
 * @param id 가맹점 id
 * @param name 상호명
 * @param category 카테고리
 * @param likes 좋아요 개수
 * @param isLiked 유저의 좋아요 여부
 * @param address 주소
 * @param coupon2 2000원 쿠폰 개수
 * @param coupon4 4000원 쿠폰 개수
 * @param reviews 리뷰들
 */

@Builder
public record FranchiseDetailRes(
	long id,
	String name,
	String category,
	int likes,
	boolean isLiked,
	String address,
	int coupon2,
	int coupon4,
	List<UserReviewRes> reviews
) {
}
