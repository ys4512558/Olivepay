package kr.co.olivepay.card.client;

import kr.co.olivepay.card.global.enums.NoneResponse;
import kr.co.olivepay.card.global.response.Response;
import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member")
public interface MemberServiceClient {

    String MEMBER_ID = "member-id";

    @GetMapping("/users/user-key")
    Response<UserKeyRes> getUserKey(@RequestHeader(MEMBER_ID) Long memberId);

    @PostMapping("/users/promote")
    Response<NoneResponse> promoteUser(@RequestHeader(MEMBER_ID) Long memberId);
}
