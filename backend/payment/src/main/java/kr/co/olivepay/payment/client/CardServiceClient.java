package kr.co.olivepay.payment.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import kr.co.olivepay.core.card.dto.req.CardSearchReq;
import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.payment.global.response.Response;

@FeignClient(name = "card", url= "http://j11a601.p.ssafy.io:8105/api")
public interface CardServiceClient {

	String MEMBER_ID = "member-id";

	@PostMapping("/cards/payment")
	ResponseEntity<Response<List<PaymentCardSearchRes>>> getPaymentCardList(
		@RequestHeader(MEMBER_ID) Long memberId,
		@RequestBody CardSearchReq cardSearchReq
	);

}
