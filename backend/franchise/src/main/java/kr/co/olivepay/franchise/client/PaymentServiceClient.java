package kr.co.olivepay.franchise.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.olivepay.core.payment.dto.res.PaymentMinimalRes;
import kr.co.olivepay.franchise.global.response.Response;

@FeignClient(name = "payment")
public interface PaymentServiceClient {

	@GetMapping("/api/payments/id/{memberId}")
	Response<List<PaymentMinimalRes>> getRecentPaymentIds(
		@PathVariable Long memberId
	);

}
