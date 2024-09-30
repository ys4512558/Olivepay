package kr.co.olivepay.franchise.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.olivepay.core.donation.dto.req.CouponListReq;
import kr.co.olivepay.core.donation.dto.res.CouponRes;
import kr.co.olivepay.franchise.global.response.Response;

@FeignClient(name = "donation")
public interface CouponServiceClient {

	@GetMapping("/api/donations/coupons/{franchiseId}")
	Response<CouponRes> getFranchiseCoupon(@PathVariable Long franchiseId);

	@PostMapping("/api/donations/coupons/")
	Response<List<CouponRes>> getFranchiseCouponList(@RequestBody CouponListReq request);

}
