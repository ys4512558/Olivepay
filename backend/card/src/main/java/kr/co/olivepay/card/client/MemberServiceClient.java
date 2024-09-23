package kr.co.olivepay.card.client;

import kr.co.olivepay.card.client.dto.req.UserKeyRes;
import kr.co.olivepay.card.global.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member", url = "${config.backend}:${config.gateway-port}/members")
public interface MemberServiceClient {

    String MEMBER_ID = "memberId";

    @GetMapping("/users/user-key")
    Response<UserKeyRes> getUserKey(@RequestHeader(MEMBER_ID) Long memberId);

}
