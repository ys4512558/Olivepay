package kr.co.olivepay.franchise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.olivepay.franchise.dto.res.LikedFranchiseRes;
import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.entity.Like;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.mapper.LikeMapper;
import kr.co.olivepay.franchise.repository.FranchiseRepository;
import kr.co.olivepay.franchise.repository.LikeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final FranchiseRepository franchiseRepository;
	private final LikeRepository likeRepository;
	private final LikeMapper likeMapper;

	/**
	 * 내가 좋아하는 가맹점 조회
	 * @param memberId
	 * @return
	 */
	@Override
	public SuccessResponse<List<LikedFranchiseRes>> getLikedFranchiseList(Long memberId) {
		List<Like> likeList = likeRepository.getAllByMemberId(memberId);
		List<LikedFranchiseRes> responseList = likeMapper.toLikedFranchiseResList(likeList);
		return new SuccessResponse<>(SuccessCode.LIKED_FRANCHISE_SEARCH_SUCCESS, responseList);
	}

	/**
	 * 특정 가맹점에 대한 좋아요 토글
	 * @param memberId
	 * @param franchiseId
	 * @return
	 */
	@Override
	@Transactional
	public SuccessResponse<NoneResponse> toggleLike(Long memberId, Long franchiseId) {
		Boolean isExist = likeRepository.existsByMemberIdAndFranchiseId(memberId, franchiseId);
		if (isExist) {
			likeRepository.deleteByMemberIdAndFranchiseId(memberId, franchiseId);
		} else {
			Franchise franchise = franchiseRepository.getById(franchiseId);
			Like like = likeMapper.toEntity(memberId, franchise);
			likeRepository.save(like);
		}

		boolean liked = !isExist;
		SuccessCode successCode = liked ? SuccessCode.LIKE_TOGGLE_ON_SUCCESS : SuccessCode.LIKE_TOGGLE_OFF_SUCCESS;
		return new SuccessResponse<>(successCode, NoneResponse.NONE);
	}

	/**
	 * 특정 가맹점의 좋아요 개수 조회
	 * @param franchiseId
	 * @return
	 */
	@Override
	public Integer getLikesCount(Long franchiseId) {
		return likeRepository.countByFranchiseId(franchiseId);
	}

	/**
	 * 특정 유저가 특정 가맹점을 좋아하는지 조회
	 * @param memberId
	 * @param franchiseId
	 * @return
	 */
	@Override
	public Boolean getLiked(Long memberId, Long franchiseId) {
		return likeRepository.existsByMemberIdAndFranchiseId(memberId, franchiseId);
	}
}
