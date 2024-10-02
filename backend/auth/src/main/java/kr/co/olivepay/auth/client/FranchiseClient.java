package kr.co.olivepay.auth.client;

import kr.co.olivepay.auth.global.response.Response;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMinimalRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "franchise")
public interface FranchiseClient {

    String MEMBER_ID = "member-id";

    @GetMapping("/api/franchises/id/{memberId}")
    Response<FranchiseMinimalRes> getFranchiseByMemberId(@PathVariable Long memberId);
}