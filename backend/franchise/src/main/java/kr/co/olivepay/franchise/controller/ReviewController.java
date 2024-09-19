package kr.co.olivepay.franchise.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.olivepay.franchise.dto.req.ReviewCreateReq;
import kr.co.olivepay.franchise.dto.res.EmptyReviewRes;
import kr.co.olivepay.franchise.dto.res.FranchiseReviewRes;
import kr.co.olivepay.franchise.dto.res.UserReviewRes;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.response.Response;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.service.ReviewService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/franchises/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
	@Operation(description = """
		리뷰를 등록합니다.
		작성자 id, 가맹점 id, 내용, 평점을 필요로 합니다.
		""", summary = "리뷰 등록")
	public ResponseEntity<Response<NoneResponse>> registerReview(
		@RequestBody ReviewCreateReq request) {
		Long memberId = 1L; //TODO: Auth 처리
		SuccessResponse<NoneResponse> response = reviewService.registerReview(memberId, request);
		return Response.success(response);
	}

	@DeleteMapping("/{reviewId}")
	@Operation(description = """
		리뷰 id에 해당하는 리뷰를 삭제합니다.
		""", summary = "리뷰 삭제")
	public ResponseEntity<Response<NoneResponse>> deleteReview(
		@PathVariable Long reviewId
	){
		Long memberId = 1L; //TODO: Auth 처리
		SuccessResponse<NoneResponse> response = reviewService.removeReview(reviewId);
		return Response.success(response);
	}

	@GetMapping("/user")
	@Operation(description = """
		내가 작성한 모든 리뷰를 조회합니다.
		20개 단위로 페이징 처리가 이뤄집니다.
		""", summary = "내가 작성한 리뷰 조회")
	public ResponseEntity<Response<List<FranchiseReviewRes>>> getMyReviewList(
	){
		Long memberId = 1L; //TODO: Auth 처리
		//TODO: 페이징 처리
		SuccessResponse<List<FranchiseReviewRes>> response = reviewService.getMyReviewList(memberId);
		return Response.success(response);
	}

	@GetMapping("/{franchiseId}")
	@Operation(description = """
		특정 가맹점에 대한 모든 리뷰를 조회합니다.
		20개 단위로 페이징 처리가 이뤄집니다.
		""", summary = "가맹점 리뷰 조회")
	public ResponseEntity<Response<List<UserReviewRes>>> getFranchiseReviewList(
		@PathVariable Long franchiseId
	){
		//TODO: 페이징 처리
		SuccessResponse<List<UserReviewRes>> response = reviewService.getFranchiseReviewList(franchiseId);
		return Response.success(response);
	}

	@GetMapping("/available")
	@Operation(description = """
		사용자가 결제 내용은 있지만 작성하지 않은 모든 리뷰를 조회합니다.
		20개 단위로 페이징 처리가 이뤄집니다.
		""", summary = "미작성 리뷰 조회")
	public ResponseEntity<Response<List<EmptyReviewRes>>> getAvailableReviewList(
	){
		Long memberId = 1L; //TODO: Auth 처리
		SuccessResponse<List<EmptyReviewRes>> response = reviewService.getAvailableReviewList(memberId);
		return Response.success(response);
	}

}
