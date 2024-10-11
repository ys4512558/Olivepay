package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 내가 좋아하는 가맹점 dto
 * @param likeId
 * @param franchiseId
 * @param franchiseName
 * @param address
 * @param latitude
 * @param longitude
 * @param category
 */
@Builder
public record LikedFranchiseRes(
	Long likeId,
	Long franchiseId,
	String franchiseName,
	String address,
	Float latitude,
	Float longitude,
	String category
) {
	
}
