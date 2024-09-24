package kr.co.olivepay.franchise.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.franchise.dto.req.*;
import kr.co.olivepay.franchise.dto.res.*;
import kr.co.olivepay.franchise.entity.*;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.response.Response;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.service.FranchiseService;
import kr.co.olivepay.franchise.service.QrService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {

	private final FranchiseService franchiseService;
	private final QrService qrService;

	@PostMapping("/owner")
	@Operation(description = """
		가맹점을 등록합니다.
		사업자등록번호, 상호명, 카테고리, 전화번호, 주소가 필요합니다.
		""", summary = "가맹점 등록")
	public ResponseEntity<Response<NoneResponse>> registerFranchise(
		@RequestBody @Valid FranchiseCreateReq request
	) {
		Long memberId = 1L; //TODO: auth 처리
		SuccessResponse<NoneResponse> response = franchiseService.registerFranchise(memberId, request);
		return Response.success(response);
	}

	@GetMapping
	@Operation(description = """
		검색한 위치 기준 500m 이내의 가맹점을 조회합니다. 
		카테고리는 필수로 입력하지 않아도 됩니다.
		""", summary = "가맹점 검색")
	public ResponseEntity<Response<List<FranchiseBasicRes>>> getFranchiseList(
		@RequestParam Double latitude,
		@RequestParam Double longitude,
		@RequestParam(required = false) Category category
	) {
		// SuccessResponse<List<FranchiseBasicRes>> response = franchiseService.getFranchiseList(latitude, longitude,
		// 	category);

		FranchiseBasicRes dto1 = FranchiseBasicRes.builder()
												  .franchiseId(1L)
												  .franchiseName("test")
												  .likes(123)
												  .avgStars(3.4)
												  .category(String.valueOf(Category.BAKERY))
												  .coupons(4)
												  .build();

		FranchiseBasicRes dto2 = FranchiseBasicRes.builder()
												  .franchiseId(2L)
												  .franchiseName("test2")
												  .likes(246)
												  .avgStars(4.8)
												  .category(String.valueOf(Category.KOREAN))
												  .coupons(8)
												  .build();

		List<FranchiseBasicRes> dtoList = new ArrayList<>();
		dtoList.add(dto1);
		dtoList.add(dto2);

		SuccessResponse<List<FranchiseBasicRes>> response = new SuccessResponse<>(SuccessCode.FRANCHISE_SEARCH_SUCCESS, dtoList);
		return Response.success(response);
	}

	@GetMapping("/{franchiseId}")
	@Operation(description = """
		가맹점 상세 정보를 조회합니다.
		isLiked의 값은 Role에 따라 null/true/false입니다.
		""", summary = "가맹점 상세 정보 조회")
	public ResponseEntity<Response<FranchiseDetailRes>> getFranchiseDetail(
		@PathVariable Long franchiseId
	) {
		Long memberId = 1L; //TODO: auth 처리
		SuccessResponse<FranchiseDetailRes> response = franchiseService.getFranchiseDetail(franchiseId);
		return Response.success(response);
	}

	@GetMapping("/name/{franchiseId}")
	@Operation(summary = "가맹점 id > 가맹점 상호명 (개발용)")
	public ResponseEntity<Response<FranchiseMinimalRes>> getFranchiseByFranchiseId(
		@PathVariable Long franchiseId
	) {
		SuccessResponse<FranchiseMinimalRes> response = franchiseService.getFranchiseByFranchiseId(franchiseId);
		return Response.success(response);
	}

	@GetMapping("/id")
	@Operation(summary = "가맹점주 id > 가맹점 id (개발용)")
	public ResponseEntity<Response<FranchiseMinimalRes>> getFranchiseByMemberId(
	) {
		Long memberId = 1L; //TODO: auth 처리
		SuccessResponse<FranchiseMinimalRes> response = franchiseService.getFranchiseByMemberId(memberId);
		return Response.success(response);
	}

	@GetMapping("/registration-number/{registrationNumber}")
	@Operation(summary = "사업자등록번호 중복 확인(개발용)")
	public ResponseEntity<Response<ExistenceRes>> checkRegistrationNumberDuplication(
		@PathVariable String registrationNumber
	){
		SuccessResponse<ExistenceRes> response = franchiseService.checkRegistrationNumberDuplication(registrationNumber);
		return Response.success(response);
	}

	@GetMapping("/qr")
	@Operation(description = """
		간편 결제를 위한 QR 코드를 생성합니다.
		가맹점 id와 결제금액을 필요로 합니다.
		""", summary = "결제 QR 코드 이미지 생성")
	public ResponseEntity<Response<QrCodeRes>> getQrCode(
		@RequestParam Long franchiseId,
		@RequestParam Integer amount
	) {
		Long memberId = 1L; //TODO: auth 처리
		SuccessResponse<QrCodeRes> response = qrService.getQrCode(franchiseId, amount);
		return Response.success(response);
	}
}
