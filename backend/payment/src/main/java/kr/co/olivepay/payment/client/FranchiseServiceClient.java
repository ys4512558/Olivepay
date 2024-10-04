package kr.co.olivepay.payment.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.olivepay.core.franchise.dto.req.FranchiseIdListReq;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMinimalRes;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMyDonationRes;
import kr.co.olivepay.payment.global.response.Response;

@FeignClient(name = "franchise")
public interface FranchiseServiceClient {

	@PostMapping("/api/franchises/list")
	Response<List<FranchiseMyDonationRes>> getFranchiseListByFranchiseIdList(
		@RequestBody FranchiseIdListReq request
	);

	@GetMapping("/api/franchises/id/{memberId}")
	Response<FranchiseMinimalRes> getFranchiseByMemberId(
		@PathVariable Long memberId
	);


}
