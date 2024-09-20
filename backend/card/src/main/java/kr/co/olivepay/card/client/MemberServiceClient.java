package kr.co.olivepay.card.client;

import kr.co.olivepay.card.client.dto.req.UserKeyRes;
import kr.co.olivepay.card.global.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member", url = "${config.backend}:8000/members")
public interface MemberServiceClient {

    @GetMapping("/users/user-key")
    Response<UserKeyRes> getUserKey(@RequestParam("memberId") Long memberId);

}
