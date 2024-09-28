package kr.co.olivepay.payment.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.dto.res.PagedPaymentHistoryRes;
import kr.co.olivepay.payment.dto.res.PaymentHistoryRes;
import kr.co.olivepay.payment.global.enums.NoneResponse;
import kr.co.olivepay.payment.global.enums.SuccessCode;
import kr.co.olivepay.payment.global.response.Response;
import kr.co.olivepay.payment.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

	//private final PaymentService paymentService;

	@PostMapping("/pay")
	@Operation(description = """
		결제를 시도합니다. \n
		필수: 가맹점 ID, 결제 금액, 간편결제 비밀번호 \n
		옵션: 추가 결제 카드 ID, 사용할 쿠폰 ID 
		""", summary = "결제")
	public ResponseEntity<Response<NoneResponse>> createPayment(@RequestBody PaymentCreateReq request) {
		SuccessResponse<NoneResponse> response = new SuccessResponse<>(SuccessCode.PAYMENT_REGISTER_SUCCESS,
			NoneResponse.NONE);
		return Response.success(response);
	}

	@GetMapping("/history/user")
	@Operation(description = """
		유저의 결제 내역을 조회합니다. \n
		""", summary = "유저 결제 내역 조회")
	public ResponseEntity<Response<PagedPaymentHistoryRes>> getUserPaymentHistory(
	) {
		List<PaymentHistoryRes> historyList = new ArrayList<>();

		PaymentHistoryRes history1 = PaymentHistoryRes.builder()
													  .paymentId(1L)
													  .amount(50000L)
													  .createdAt(LocalDateTime.now())
													  .build();
		PaymentHistoryRes history2 = PaymentHistoryRes.builder()
													  .paymentId(2L)
													  .amount(8000L)
													  .createdAt(LocalDateTime.now())
													  .build();

		historyList.add(history1);
		historyList.add(history2);
		PagedPaymentHistoryRes pagedHistoryList = PagedPaymentHistoryRes.builder()
																.nextIndex(1L)
																.history(historyList)
																.build();
		SuccessResponse<PagedPaymentHistoryRes> response = new SuccessResponse<>(SuccessCode.USER_PAYMENT_HISTORY_SUCCESS, pagedHistoryList);
		return Response.success(response);
	}

	@GetMapping("/history/{franchiseId}")
	@Operation(description = """
		가맹점 결제 내역을 조회합니다. \n
		결제 내역을 조회하고 싶은 가맹점 ID를 필요로 합니다.
		""", summary = "가맹점 결제 내역 조회")
	public ResponseEntity<Response<PagedPaymentHistoryRes>> getFranchisePaymentHistory(
		@PathVariable Long franchiseId
	) {
		List<PaymentHistoryRes> historyList = new ArrayList<>();
		PaymentHistoryRes history1 = PaymentHistoryRes.builder()
													  .paymentId(1L)
													  .amount(50000L)
													  .details(null)
													  .createdAt(LocalDateTime.now())
													  .build();
		PaymentHistoryRes history2 = PaymentHistoryRes.builder()
													  .paymentId(2L)
													  .amount(8000L)
													  .details(null)
													  .createdAt(LocalDateTime.now())
													  .build();

		historyList.add(history1);
		historyList.add(history2);
		PagedPaymentHistoryRes pagedHistoryList = PagedPaymentHistoryRes.builder()
																		.nextIndex(-1L)
																		.history(historyList)
																		.build();
		SuccessResponse<PagedPaymentHistoryRes> response = new SuccessResponse<>(SuccessCode.FRANCHISE_PAYMENT_HISTORY_SUCCESS, pagedHistoryList);
		return Response.success(response);
	}

}
