package kr.co.olivepay.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import kr.co.olivepay.core.member.dto.req.UserPinCheckReq;
import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import kr.co.olivepay.payment.global.response.Response;

@FeignClient(name = "member", url = "http://j11a601.p.ssafy.io:8101/api")
public interface MemberServiceClient {

	String MEMBER_ID = "member-id";

	@GetMapping("/members/users/user-key")
	Response<UserKeyRes> getUserKey(@RequestHeader(MEMBER_ID) Long memberId);

	@PostMapping("/members/users/pin-check")
	Response<UserKeyRes> checkUserPin(
		@RequestHeader(MEMBER_ID) Long memberId,
		@RequestBody UserPinCheckReq request);

}
