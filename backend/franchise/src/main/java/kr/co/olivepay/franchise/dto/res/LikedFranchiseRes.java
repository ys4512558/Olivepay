package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 내가 좋아하는 가맹점 dto
 * @param likeId
 * @param franchise
 */
@Builder
public record LikedFranchiseRes(
	Long likeId,
	FranchiseMinimalRes franchise
) {
	
}
