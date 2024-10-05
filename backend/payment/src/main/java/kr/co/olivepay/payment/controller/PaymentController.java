package kr.co.olivepay.payment.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.core.global.dto.res.PageResponse;
import kr.co.olivepay.core.util.CommonUtil;
import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.dto.res.PaymentHistoryFranchiseRes;
import kr.co.olivepay.payment.dto.res.PaymentHistoryRes;
import kr.co.olivepay.payment.global.enums.NoneResponse;
import kr.co.olivepay.payment.global.response.Response;
import kr.co.olivepay.payment.global.response.SuccessResponse;
import kr.co.olivepay.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/pay")
	@Operation(description = """
		결제를 시도합니다. \n
		필수: 가맹점 ID, 결제 금액, 간편결제 비밀번호 \n
		옵션: 추가 결제 카드 ID, 사용할 쿠폰 ID 
		""", summary = "결제")
	public ResponseEntity<Response<NoneResponse>> createPayment(
		@RequestHeader HttpHeaders headers,
		@RequestBody @Valid PaymentCreateReq request
	) {
		Long memberId = CommonUtil.getMemberId(headers);
		SuccessResponse<NoneResponse> response = paymentService.pay(memberId, request);
		return Response.success(response);
	}

	@GetMapping("/history/user")
	@Operation(description = """
		유저의 결제 내역을 20개씩 조회합니다. \n
		첫 호출 시에는 index를 별도로 지정하지 않고 호출하면 됩니다. \n
		이후 호출 시에는 반환된 nextIndex를 ?index={nextIndex} 형태로 파라미터에 추가해서 호출해주시면 됩니다.
		""", summary = "유저 결제 내역 조회")
	public ResponseEntity<Response<PageResponse<List<PaymentHistoryFranchiseRes>>>> getUserPaymentHistory(
		@RequestHeader HttpHeaders headers,
		@RequestParam(required = false) Long index
	) {
		Long memberId = CommonUtil.getMemberId(headers);
		SuccessResponse<PageResponse<List<PaymentHistoryFranchiseRes>>> response = paymentService.getUserPaymentHistory(
			memberId, index);
		return Response.success(response);
	}

	@GetMapping("/history/{franchiseId}")
	@Operation(description = """
		가맹점 거래 내역을 20개씩 조회합니다. \n
		결제 내역을 조회하고 싶은 가맹점 ID를 필요로 하며, 가맹점에 대한 소유권이 있는 경우에만 정상적 응답이 반환됩니다. \n
		첫 호출 시에는 index를 별도로 지정하지 않고 호출하면 됩니다. \n
		이후 호출 시에는 반환된 nextIndex를 ?index={nextIndex} 형태로 파라미터에 추가해서 호출해주시면 됩니다.
		""", summary = "가맹점 결제 내역 조회")
	public ResponseEntity<Response<PageResponse<List<PaymentHistoryRes>>>> getFranchisePaymentHistory(
		@RequestHeader HttpHeaders headers,
		@PathVariable Long franchiseId,
		@RequestParam(required = false) Long index
	) {

		Long memberId = CommonUtil.getMemberId(headers);
		SuccessResponse<PageResponse<List<PaymentHistoryRes>>> response = paymentService.getFranchisePaymentHistory(
			memberId, franchiseId, index);
		return Response.success(response);
	}

}
