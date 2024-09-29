package kr.co.olivepay.member.client;

import kr.co.olivepay.core.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.core.franchise.dto.res.ExistenceRes;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "franchise")
public interface FranchiseClient {

    String MEMBER_ID = "member-id";

    @GetMapping("/api/franchises/registration-number/{registrationNumber}")
    Response<ExistenceRes> checkRegistrationNumberDuplication(@PathVariable String registrationNumber);

    @PostMapping("/api/franchises/owner")
    Response<NoneResponse> registerFranchise(
            @RequestHeader(MEMBER_ID) Long memberId,
            @RequestBody FranchiseCreateReq franchiseCreateReq);

}
