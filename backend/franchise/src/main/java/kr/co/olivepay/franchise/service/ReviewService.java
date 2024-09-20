package kr.co.olivepay.franchise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.dto.req.ReviewCreateReq;
import kr.co.olivepay.franchise.dto.res.EmptyReviewRes;
import kr.co.olivepay.franchise.dto.res.FranchiseReviewRes;
import kr.co.olivepay.franchise.dto.res.UserReviewRes;
import kr.co.olivepay.franchise.entity.Review;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;

@Service
public interface ReviewService {

	/**
	 * 리뷰 등록
	 * @param memberId
	 * @param request
	 * @return
	 */
	SuccessResponse<NoneResponse> registerReview(Long memberId, ReviewCreateReq request);

	/**
	 * 리뷰 삭제
	 * @param reviewId
	 * @return
	 */
	SuccessResponse<NoneResponse> removeReview(Long reviewId);

	/**
	 * 내가 작성한 리뷰 조회
	 * @param memberId
	 * @return
	 */
	SuccessResponse<List<FranchiseReviewRes>> getMyReviewList(Long memberId);

	/**
	 * 특정 가맹점의 리뷰 조회
	 * @param franchiseId
	 * @return
	 */
	SuccessResponse<List<UserReviewRes>> getFranchiseReviewList(Long franchiseId);

	/**
	 * 작성 가능한 리뷰 조회
	 * @param memberId
	 * @return
	 */
	SuccessResponse<List<EmptyReviewRes>> getAvailableReviewList(Long memberId);

}
