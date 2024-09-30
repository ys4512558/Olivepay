package kr.co.olivepay.funding.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.olivepay.funding.dto.req.FundingCreateReq;
import kr.co.olivepay.funding.dto.req.FundingUsageCreateReq;
import kr.co.olivepay.funding.dto.res.FundingAmountRes;
import kr.co.olivepay.funding.dto.res.FundingUsageRes;
import kr.co.olivepay.funding.global.enums.NoneResponse;
import kr.co.olivepay.funding.global.response.Response;
import kr.co.olivepay.funding.global.response.SuccessResponse;
import kr.co.olivepay.funding.service.FundingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fundings")
@RequiredArgsConstructor
public class FundingController {

	private final FundingService fundingService;

	@GetMapping("/total")
	@Operation(description = "공용 기부금의 총액을 조회합니다.", summary = "공용 기부금 총액 조회")
	public final ResponseEntity<Response<FundingAmountRes>> getTotalFundingAmount(
	) {
		SuccessResponse<FundingAmountRes> response = fundingService.getTotalFundingAmount();
		return Response.success(response);
	}

	@GetMapping("/usage")
	@Operation(description = "공용 기부금 사용 내역을 조회합니다.", summary = "공용 기부금 사용 내역 조회")
	public final ResponseEntity<Response<List<FundingUsageRes>>> getDonationUsageHistory() {
		SuccessResponse<List<FundingUsageRes>> response = fundingService.getDonationUsageHistory();
		return Response.success(response);
	}

	@PostMapping("/fund")
	@Operation(description = "공용 기부금으로 잔액을 이체합니다.", summary = "공용 기부금으로 잔액 이체 (개발용)")
	public final ResponseEntity<Response<NoneResponse>> createFunding(
		@RequestBody FundingCreateReq request
	){
		SuccessResponse<NoneResponse> response = fundingService.createFunding(request);
		return Response.success(response);
	}

	@PostMapping("/donate")
	@Operation(description = "공용 기부금의 돈을 기부단체에 기부합니다.", summary = "기부 (개발용)")
	public final ResponseEntity<Response<NoneResponse>> createFundingUsage(
		@RequestBody FundingUsageCreateReq request
	){
		SuccessResponse<NoneResponse> response = fundingService.createFundingUsage(request);
		return Response.success(response);
	}

}
