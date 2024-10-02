package kr.co.olivepay.franchise.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import kr.co.olivepay.core.member.dto.req.UserNicknamesReq;
import kr.co.olivepay.core.member.dto.res.UserNicknamesRes;
import kr.co.olivepay.franchise.global.response.Response;

@FeignClient(name = "member")
public interface MemberServiceClient {

	@PostMapping("/api/members/users/nickname")
	ResponseEntity<Response<UserNicknamesRes>> getUserNicknames(
		@RequestBody @Valid UserNicknamesReq request);

}
