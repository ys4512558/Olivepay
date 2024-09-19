package kr.co.olivepay.franchise.dto.res;

import java.util.List;

import lombok.Builder;

/**
 * 가맹점 상세 검색(유저)을 위한 dto
 */

@Builder
public class FranchiseDetailLikeRes extends FranchiseDetailRes {
	Boolean isLiked;
}
