package kr.co.olivepay.franchise.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.olivepay.core.util.CommonUtil;
import kr.co.olivepay.franchise.dto.res.LikedFranchiseRes;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
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
		내가 좋아요를 누른 모든 가맹점을 조회합니다. \n
		가맹점 id와 가맹점 상호명 List를 반환합니다.
		""", summary = "내가 좋아하는 가맹점 조회")
	public ResponseEntity<Response<List<LikedFranchiseRes>>> getLikedFranchiseList(
		@RequestHeader HttpHeaders headers
	) {
		Long memberId = CommonUtil.getMemberId(headers);
		SuccessResponse<List<LikedFranchiseRes>> response = likeService.getLikedFranchiseList(memberId);
		return Response.success(response);
	}

	@PostMapping("/user/{franchiseId}")
	@Operation(summary = "가맹점에 대한 좋아요 토글")
	public ResponseEntity<Response<NoneResponse>> toggleLike(
		@RequestHeader HttpHeaders headers,
		@PathVariable Long franchiseId) {
		Long memberId = CommonUtil.getMemberId(headers);
		SuccessResponse<NoneResponse> response = likeService.toggleLike(memberId, franchiseId);
		return Response.success(response);
	}

}
