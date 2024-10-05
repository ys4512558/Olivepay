package kr.co.olivepay.franchise.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.olivepay.core.global.dto.res.PageResponse;
import kr.co.olivepay.core.member.dto.req.UserNicknamesReq;
import kr.co.olivepay.core.member.dto.res.UserNicknameRes;
import kr.co.olivepay.core.payment.dto.res.PaymentMinimalRes;
import kr.co.olivepay.franchise.client.MemberServiceClient;
import kr.co.olivepay.franchise.client.PaymentServiceClient;
import kr.co.olivepay.franchise.dto.req.ReviewCreateReq;
import kr.co.olivepay.franchise.dto.res.EmptyReviewRes;
import kr.co.olivepay.franchise.dto.res.FranchiseReviewRes;
import kr.co.olivepay.franchise.dto.res.UserReviewRes;
import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.entity.Review;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.handler.AppException;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.mapper.ReviewMapper;
import kr.co.olivepay.franchise.repository.ReviewRepository;
import kr.co.olivepay.franchise.repository.FranchiseRepository;
import kr.co.olivepay.franchise.service.FranchiseService;
import kr.co.olivepay.franchise.service.ReviewService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private static final String UNKNOWN_USER = "알 수 없는 사용자";

	private final FranchiseService franchiseService;
	private final ReviewRepository reviewRepository;
	private final FranchiseRepository franchiseRepository;
	private final ReviewMapper reviewMapper;

	private final MemberServiceClient memberServiceClient;
	private final PaymentServiceClient paymentServiceClient;

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
	public SuccessResponse<PageResponse<List<FranchiseReviewRes>>> getMyReviewList(Long memberId, Long index) {
		List<Review> reviewList = reviewRepository.findAllByMemberIdAfterIndex(memberId, index);
		List<FranchiseReviewRes> reviewResList = reviewMapper.toFranchiseReviewResList(reviewList);
		long nextIndex = (reviewList.isEmpty()) ? index : reviewList.get(reviewList.size() - 1)
																	.getId();

		PageResponse<List<FranchiseReviewRes>> response = new PageResponse<>(nextIndex, reviewResList);

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
	public SuccessResponse<PageResponse<List<UserReviewRes>>> getFranchiseReviewList(Long franchiseId, Long index) {
		List<Review> reviewList = reviewRepository.findAllByFranchiseIdAfterIndex(franchiseId, index);
		List<UserNicknameRes> userNicknameResList = getUserNicknameResList(reviewList);
		List<UserReviewRes> reviewResList = buildUserReviewResList(reviewList, userNicknameResList);
		long nextIndex = (reviewList.isEmpty()) ? index : reviewList.get(reviewList.size() - 1)
																	.getId();

		PageResponse<List<UserReviewRes>> response = new PageResponse<>(nextIndex, reviewResList);

		return new SuccessResponse<>(
			SuccessCode.FRANCHISE_REVIEW_SEARCH_SUCCESS,
			response
		);
	}

	private List<UserNicknameRes> getUserNicknameResList(List<Review> reviewList) {
		List<Long> memberIdList = reviewList.stream()
											.map(Review::getMemberId)
											.toList();

		UserNicknamesReq request = reviewMapper.toUserNicknamesReq(memberIdList);
		List<UserNicknameRes> userNicknameResList = null;
		try {
			userNicknameResList = memberServiceClient.getUserNicknames(request)
																		   .getBody()
																		   .data()
																		   .members();
		} catch (Exception e) {
			throw new AppException(ErrorCode.MEMBER_FEIGN_CLIENT_ERROR);
		}

		return userNicknameResList;
	}

	private List<UserReviewRes> buildUserReviewResList(List<Review> reviewList,
		List<UserNicknameRes> userNicknamResList) {
		Map<Long, String> userNicknameMap = createUserNicknameMap(userNicknamResList);
		return reviewList.stream()
						 .map(review -> reviewMapper.toUserReviewRes(review, userNicknameMap.getOrDefault(review.getMemberId(), UNKNOWN_USER)))
						 .toList();
	}

	private Map<Long, String> createUserNicknameMap(List<UserNicknameRes> userNicknameRes) {
		return userNicknameRes
			.stream()
			.collect(Collectors.toMap(UserNicknameRes::memberId,
				UserNicknameRes::nickname));
	}

	@Override
	public SuccessResponse<List<EmptyReviewRes>> getAvailableReviewList(Long memberId) {

		List<PaymentMinimalRes> paymentList = null;
		try {
			paymentList = paymentServiceClient.getRecentPaymentIds(memberId)
											  .data();
		} catch (Exception e) {
			throw new AppException(ErrorCode.PAYMENT_FEIGN_CLIENT_ERROR);
		}

		List<EmptyReviewRes> emptyReviewResList = new ArrayList<>();
		for (PaymentMinimalRes payment : paymentList) {
			if (reviewRepository.existsByMemberIdAndPaymentId(memberId, payment.paymentId()))
				continue;

			EmptyReviewRes emptyReviewRes = reviewMapper.toEmptyReviewRes(emptyReviewResList.size() + 1,
				franchiseService.getFranchiseByFranchiseId(payment.franchiseId())
								.data(), payment);
			emptyReviewResList.add(emptyReviewRes);
		}

		return new SuccessResponse<>(SuccessCode.AVAILABLE_REVIEW_SEARCH_SUCCESS, emptyReviewResList);
	}


}
