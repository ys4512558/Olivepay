package kr.co.olivepay.card.client;

import kr.co.olivepay.card.global.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member")
public interface MemberServiceClient {

    @GetMapping("/members/users/user-key")
    Response<String> getUserKey(@RequestParam("memberId") Long memberId);

}
