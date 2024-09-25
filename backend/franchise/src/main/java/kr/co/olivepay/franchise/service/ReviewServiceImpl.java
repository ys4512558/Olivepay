package kr.co.olivepay.franchise.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.Fraction;
import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.dto.req.ReviewCreateReq;
import kr.co.olivepay.franchise.dto.res.EmptyReviewRes;
import kr.co.olivepay.franchise.dto.res.FranchiseReviewRes;
import kr.co.olivepay.franchise.dto.res.PagedFranchiseReviewsRes;
import kr.co.olivepay.franchise.dto.res.PagedUserReviewsRes;
import kr.co.olivepay.franchise.dto.res.UserReviewRes;
import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.entity.Review;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.mapper.ReviewMapper;
import kr.co.olivepay.franchise.repository.ReviewRepository;
import kr.co.olivepay.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final FranchiseRepository franchiseRepository;
	private final ReviewMapper reviewMapper;

	/**
	 * 리뷰 등록
	 * @param memberId
	 * @param request
	 * @return
	 */
	@Override
	public SuccessResponse<NoneResponse> registerReview(Long memberId, ReviewCreateReq request) {
		Franchise franchise = franchiseRepository.getById(request.franchiseId());
		Review review = reviewMapper.toEntity(memberId, request, franchiseRepository);
		reviewRepository.save(review);
		return new SuccessResponse<>(SuccessCode.REVIEW_REGISTER_SUCCESS, NoneResponse.NONE);
	}

	/**
	 * 리뷰 삭제
	 * @param reviewId
	 * @return
	 */
	@Override
	public SuccessResponse<NoneResponse> removeReview(Long reviewId) {
		reviewRepository.deleteById(reviewId);
		return new SuccessResponse<>(SuccessCode.REVIEW_DELETE_SUCCESS, NoneResponse.NONE);
	}

	/**
	 * 내가 작성한 리뷰 조회
	 * @param memberId
	 * @return
	 */
	@Override
	public SuccessResponse<PagedFranchiseReviewsRes> getMyReviewList(Long memberId, Long index) {
		List<Review> reviewList = reviewRepository.findAllByMemberIdAfterIndex(memberId, index);
		List<FranchiseReviewRes> reviewResList = reviewMapper.toFranchiseReviewResList(reviewList);
		long nextIndex = reviewList.isEmpty() ? -1 : reviewList.get(reviewList.size() - 1)
															   .getId();
		PagedFranchiseReviewsRes response = reviewMapper
			.toPagedFranchiseReviewRes(nextIndex, reviewResList);
		return new SuccessResponse<>(
			SuccessCode.USER_REVIEW_SEARCH_SUCCESS,
			response
		);
	}

	/**
	 * 특정 가맹점의 리뷰 조회
	 * @param franchiseId
	 * @return
	 */
	@Override
	public SuccessResponse<PagedUserReviewsRes> getFranchiseReviewList(Long franchiseId, Long index) {
		List<Review> reviewList = reviewRepository.findAllByFranchiseIdAfterIndex(franchiseId, index);

		//TODO: memberName 채워넣기
		List<UserReviewRes> reviewResList = reviewMapper.toUserReviewResList(reviewList);
		long nextIndex = reviewList.isEmpty() ? -1 : reviewList.get(reviewList.size() - 1)
															   .getId();
		PagedUserReviewsRes response = reviewMapper
			.toPagedUserReviewsRes(nextIndex, reviewResList);
		return new SuccessResponse<>(
			SuccessCode.FRANCHISE_REVIEW_SEARCH_SUCCESS,
			response
		);
	}

	@Override
	public SuccessResponse<List<EmptyReviewRes>> getAvailableReviewList(Long memberId) {
		return null;
	}
}
