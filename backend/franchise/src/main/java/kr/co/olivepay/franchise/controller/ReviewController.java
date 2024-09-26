package kr.co.olivepay.franchise.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.olivepay.franchise.dto.req.ReviewCreateReq;
import kr.co.olivepay.franchise.dto.res.EmptyReviewRes;
import kr.co.olivepay.franchise.dto.res.FranchiseMinimalRes;
import kr.co.olivepay.franchise.dto.res.FranchiseReviewRes;
import kr.co.olivepay.franchise.dto.res.PagedFranchiseReviewsRes;
import kr.co.olivepay.franchise.dto.res.PagedUserReviewsRes;
import kr.co.olivepay.franchise.dto.res.UserReviewRes;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.response.Response;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.service.ReviewService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/franchises/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/user")
	@Operation(description = """
		리뷰를 등록합니다. \n
		작성자 id, 가맹점 id, 내용, 평점을 필요로 합니다.
		""", summary = "리뷰 등록")
	public ResponseEntity<Response<NoneResponse>> registerReview(
		@RequestBody ReviewCreateReq request) {
		Long memberId = 1L; //TODO: Auth 처리
		SuccessResponse<NoneResponse> response = reviewService.registerReview(memberId, request);
		return Response.success(response);
	}

	@DeleteMapping("/user/{reviewId}")
	@Operation(description = """
		리뷰 id에 해당하는 리뷰를 삭제합니다.
		""", summary = "리뷰 삭제")
	public ResponseEntity<Response<NoneResponse>> deleteReview(
		@PathVariable Long reviewId
	) {
		Long memberId = 1L; //TODO: Auth 처리
		SuccessResponse<NoneResponse> response = reviewService.removeReview(reviewId);
		return Response.success(response);
	}

	@GetMapping("/user")
	@Operation(description = """
		내가 작성한 모든 리뷰를 조회합니다. \n
		20개 단위로 페이징 처리가 이뤄집니다.
		""", summary = "내가 작성한 리뷰 조회")
	public ResponseEntity<Response<PagedFranchiseReviewsRes>> getMyReviewList(
		@RequestParam(defaultValue = "0") Long index
	) {
		Long memberId = 1L; //TODO: Auth 처리
		SuccessResponse<PagedFranchiseReviewsRes> response = reviewService.getMyReviewList(memberId, index);
		return Response.success(response);
	}

	@GetMapping("/{franchiseId}")
	@Operation(description = """
		특정 가맹점에 대한 모든 리뷰를 조회합니다. \n
		20개 단위로 페이징 처리가 이뤄집니다.
		""", summary = "가맹점 리뷰 조회")
	public ResponseEntity<Response<PagedUserReviewsRes>> getFranchiseReviewList(
		@PathVariable Long franchiseId,
		@RequestParam(defaultValue = "0") Long index
	) {
		SuccessResponse<PagedUserReviewsRes> response = reviewService.getFranchiseReviewList(franchiseId, index);
		return Response.success(response);
	}

	@GetMapping("/available")
	@Operation(description = """
		사용자가 결제 내용은 있지만 작성하지 않은 모든 리뷰를 조회합니다. \n
		20개 단위로 페이징 처리가 이뤄집니다.
		""", summary = "미작성 리뷰 조회")
	public ResponseEntity<Response<List<EmptyReviewRes>>> getAvailableReviewList(
	) {
		// Long memberId = 1L; //TODO: Auth 처리
		// SuccessResponse<List<EmptyReviewRes>> response = reviewService.getAvailableReviewList(memberId);

		FranchiseMinimalRes franchise1 = FranchiseMinimalRes.builder()
															.id(1L)
															.name("멀티 캠퍼스")
															.build();
		FranchiseMinimalRes franchise2 = FranchiseMinimalRes.builder()
															.id(2L)
															.name("아웃백 스테이크하우스")
															.build();

		EmptyReviewRes dto1 = EmptyReviewRes.builder()
											.reviewId(11111L)
											.franchise(franchise1)
											.createdAt(LocalDateTime.parse("2024-09-20T14:09:12"))
											.build();
		EmptyReviewRes dto2 = EmptyReviewRes.builder()
											.reviewId(22222L)
											.franchise(franchise2)
											.createdAt(LocalDateTime.parse("2024-08-11T12:32:54"))
											.build();

		List<EmptyReviewRes> dtoList = new ArrayList<>();
		dtoList.add(dto1);
		dtoList.add(dto2);
		SuccessResponse<List<EmptyReviewRes>> response = new SuccessResponse<>(
			SuccessCode.AVAILABLE_REVIEW_SEARCH_SUCCESS, dtoList);

		return Response.success(response);
	}

}
