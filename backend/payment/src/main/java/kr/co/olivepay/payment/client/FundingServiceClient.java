package kr.co.olivepay.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.olivepay.core.funding.dto.req.FundingCreateReq;
import kr.co.olivepay.payment.global.enums.NoneResponse;
import kr.co.olivepay.payment.global.response.Response;

@FeignClient(name = "funding")
public interface FundingServiceClient {

	@PostMapping("/fundings/fund")
	Response<NoneResponse> createFunding(
		@RequestBody FundingCreateReq request
	);

}
