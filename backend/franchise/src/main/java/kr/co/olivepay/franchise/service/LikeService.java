package kr.co.olivepay.franchise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.dto.res.ExistenceRes;
import kr.co.olivepay.franchise.dto.res.LikedFranchiseRes;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.response.SuccessResponse;

@Service
public interface LikeService {
	/**
	 * 내가 좋아하는 가맹점 조회
	 * @param memberId
	 * @return
	 */
	SuccessResponse<List<LikedFranchiseRes>> getLikedFranchiseList(Long memberId);

	/**
	 * 특정 가맹점에 대한 좋아요 토글
	 * @param memberId
	 * @param franchiseId
	 * @return
	 */
	SuccessResponse<NoneResponse> toggleLike(Long memberId, Long franchiseId);

	/**
	 * 특정 가맹점의 좋아요 개수 조회
	 * @param franchiseId
	 * @return
	 */
	Integer getLikesCount(Long franchiseId);

	/**
	 * 특정 유저가 특정 가맹점을 좋아하는지 조회
	 * @param memberId
	 * @param franchiseId
	 * @return
	 */
	Boolean getLiked(Long memberId, Long franchiseId);

}
