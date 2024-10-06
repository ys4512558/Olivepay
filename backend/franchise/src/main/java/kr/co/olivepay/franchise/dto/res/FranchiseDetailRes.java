package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 가맹점 상세 검색을 위한 dto
 */

@Builder
public record FranchiseDetailRes(
	Long franchiseId,
	String franchiseName,
	String telephoneNumber,
	String category,
	String address,
	Long coupon2,
	Long coupon4,
	Long likes,
	Boolean isLiked,
	Long reviews
) {
}
