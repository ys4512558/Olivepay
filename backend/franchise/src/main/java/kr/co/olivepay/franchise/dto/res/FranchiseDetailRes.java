package kr.co.olivepay.franchise.dto.res;

import java.util.Optional;

import lombok.Builder;

/**
 * 가맹점 상세 검색을 위한 dto
 */

@Builder
public record FranchiseDetailRes(
	Long franchiseId,
	String franchiseName,
	String category,
	String address,
	Integer coupon2,
	Integer coupon4,
	Integer likes,
	Optional<Boolean> isLiked
) {
}
