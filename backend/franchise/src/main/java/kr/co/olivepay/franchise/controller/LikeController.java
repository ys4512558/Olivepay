package kr.co.olivepay.franchise.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.olivepay.franchise.dto.res.FranchiseBasicRes;
import kr.co.olivepay.franchise.dto.res.FranchiseMinimalRes;
import kr.co.olivepay.franchise.dto.res.LikedFranchiseRes;
import kr.co.olivepay.franchise.entity.Category;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.response.Response;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.service.LikeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/franchises/likes")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@GetMapping("/user")
	@Operation(description = """
		내가 좋아요를 누른 모든 가맹점을 조회합니다.
		가맹점 id와 가맹점 상호명 List를 반환합니다.
		""", summary = "내가 좋아하는 가맹점 조회")
	public ResponseEntity<Response<List<LikedFranchiseRes>>> getLikedFranchiseList(
	) {
		// Long memberId = 1L; //TODO: auth 처리
		// SuccessResponse<List<LikedFranchiseRes>> response = likeService.getLikedFranchiseList(memberId);

		FranchiseMinimalRes franchise1 = FranchiseMinimalRes.builder()
															.id(1L)
															.name("멀티 캠퍼스")
															.build();
		LikedFranchiseRes dto1 = LikedFranchiseRes.builder()
												  .likeId(11111L)
												  .franchise(franchise1)
												  .build();

		FranchiseMinimalRes franchise2 = FranchiseMinimalRes.builder()
															.id(2L)
															.name("아웃백 스테이크하우스")
															.build();
		LikedFranchiseRes dto2 = LikedFranchiseRes.builder()
												  .likeId(222222L)
												  .franchise(franchise2)
												  .build();

		List<LikedFranchiseRes> dtoList = new ArrayList<>();
		dtoList.add(dto1);
		dtoList.add(dto2);

		SuccessResponse<List<LikedFranchiseRes>> response = new SuccessResponse<>(SuccessCode.LIKED_FRANCHISE_SEARCH_SUCCESS, dtoList);
		return Response.success(response);
	}

	@PostMapping("/{franchiseId}")
	@Operation(summary = "가맹점에 대한 좋아요 토글")
	public ResponseEntity<Response<NoneResponse>> toggleLike(
		@PathVariable Long franchiseId) {
		Long memberId = 1L; //TODO: auth 처리
		SuccessResponse<NoneResponse> response = likeService.toggleLike(memberId, franchiseId);
		return Response.success(response);
	}

}
