package kr.co.olivepay.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.core.donation.dto.req.CouponListReq;
import kr.co.olivepay.donation.dto.res.CouponMyRes;
import kr.co.olivepay.core.donation.dto.res.CouponRes;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import kr.co.olivepay.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static kr.co.olivepay.donation.global.enums.SuccessCode.*;

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
    public ResponseEntity<Response<CouponRes>> getFranchiseCoupon(
            @PathVariable Long franchiseId
    ) {
        SuccessResponse<CouponRes> response = donationService.getFranchiseCoupon(franchiseId);
        return Response.success(response);
    }

    @PostMapping
    @Operation(description = """
            가맹점 리스트 쿠폰 조회 API **(개발용)** \n
            쿠폰을 조회 하고 싶은 가맹점들의 id 리스트가 필요합니다. \n
            가맹점 id, 2000/4000 쿠폰 개수 리스트를 반환합니다. \n
            """, summary = "가맹점 리스트 쿠폰 조회 API(개발용)")
    public ResponseEntity<Response<List<CouponRes>>> getMyDonation(
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
    public ResponseEntity<Response<List<CouponMyRes>>> getMyDonation(
            @RequestParam(required = false) Long franchiseId
    ) {
        List<CouponMyRes> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CouponMyRes res = CouponMyRes.builder()
                    .couponUnit("2000")
                    .franchiseId(franchiseId)
                    .franchiseName("돈까스")
                    .message("맛있게 먹자")
                    .build();
            list.add(res);
        }
        SuccessResponse response = new SuccessResponse(
                COUPON_MY_LIST_GET_SUCCESS,
                list
        );
        return Response.success(response);
    }

}
