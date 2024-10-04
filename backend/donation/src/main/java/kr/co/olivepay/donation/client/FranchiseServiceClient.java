package kr.co.olivepay.donation.client;

import kr.co.olivepay.core.franchise.dto.req.FranchiseIdListReq;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMyDonationRes;
import kr.co.olivepay.donation.global.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "franchise")
public interface FranchiseServiceClient {
    @PostMapping("/api/franchises/list")
    Response<List<FranchiseMyDonationRes>> getFranchiseInfos(@RequestBody FranchiseIdListReq request);
}
