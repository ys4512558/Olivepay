package kr.co.olivepay.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.core.donation.dto.req.CouponListReq;
import kr.co.olivepay.core.donation.dto.res.CouponRes;
import kr.co.olivepay.core.util.CommonUtil;
import kr.co.olivepay.donation.dto.req.CouponGetReq;
import kr.co.olivepay.donation.dto.res.CouponDetailRes;
import kr.co.olivepay.donation.dto.res.CouponMyRes;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import kr.co.olivepay.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donations/coupons")
public class CouponController {

    private final DonationService donationService;

    @GetMapping("/{franchiseId}")
    @Operation(description = """
            가맹점 쿠폰 조회 API \n
            가맹점 id, 2000/4000 쿠폰 개수를 반환합니다. \n
            """, summary = "가맹점 쿠폰 조회 API")
    public ResponseEntity<Response<CouponDetailRes>> getFranchiseCoupon(
            @PathVariable Long franchiseId
    ) {
        SuccessResponse<CouponDetailRes> response = donationService.getFranchiseCoupon(franchiseId);
        return Response.success(response);
    }

    @PostMapping
    @Operation(description = """
            가맹점 리스트 쿠폰 조회 API **(개발용)** \n
            쿠폰을 조회 하고 싶은 가맹점들의 id 리스트가 필요합니다. \n
            가맹점 id, 2000/4000 쿠폰 개수 리스트를 반환합니다. \n
            """, summary = "가맹점 리스트 쿠폰 조회 API(개발용)")
    public ResponseEntity<Response<List<CouponRes>>> getFranchiseListCoupon(
            @RequestBody @Valid CouponListReq request
    ) {
        SuccessResponse<List<CouponRes>> response = donationService.getFranchiseListCoupon(request);
        return Response.success(response);
    }

    @GetMapping("/my")
    @Operation(description = """
            사용자 보유 큐폰 조회 API \n
            사용자가 가진 모든 쿠폰 리스트가 반환됩니다. \n
            만약 query param으로 가맹점 id가 존재한다면 해당 가맹점의 쿠폰 리스트만 반환됩니다. \n
            가맹점 id, 가맹점 이름, 쿠폰 단위, 쿠폰 메시지 리스트를 반환합니다. \n
            """, summary = "가맹점 리스트 쿠폰 조회 API")
    public ResponseEntity<Response<List<CouponMyRes>>> getMyCoupon(
            @RequestParam(required = false) Long franchiseId,
            @RequestHeader HttpHeaders headers
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        SuccessResponse<List<CouponMyRes>> response = donationService.getMyCoupon(memberId, franchiseId);
        return Response.success(response);
    }

    @PostMapping("/my")
    @Operation(description = """
            쿠폰 획득 API \n
            쿠폰 단위와 가맹점 아이디를 통해 쿠폰을 획득합니다. \n
            """, summary = "쿠폰 획득 API")
    public ResponseEntity<Response<NoneResponse>> getCoupon(
            @RequestBody CouponGetReq request,
            @RequestHeader HttpHeaders headers
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        SuccessResponse<NoneResponse> response = donationService.getCoupon(memberId, request);
        return Response.success(response);
    }

}
