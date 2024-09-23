package kr.co.olivepay.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.donation.dto.req.CouponListReq;
import kr.co.olivepay.donation.dto.res.CouponMyRes;
import kr.co.olivepay.donation.dto.res.CouponRes;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static kr.co.olivepay.donation.global.enums.SuccessCode.*;

@RestController
@RequestMapping("/donations/coupons")
public class CouponController {

    @GetMapping("/{franchiseId}")
    @Operation(description = """
            가맹점 쿠폰 조회 API
            가맹점 id, 2000/4000 쿠폰 개수를 반환합니다.
            """, summary = "가맹점 쿠폰 조회 API")
    public ResponseEntity<Response<CouponRes>> getFranchiseCoupon(
            @PathVariable Long franchiseId
    ) {
        SuccessResponse response = new SuccessResponse(
                COUPON_GET_SUCCESS,
                CouponRes.builder()
                        .franchiseId(franchiseId)
                        .coupon2(20L)
                        .coupon4(40L)
                        .build()
        );
        return Response.success(response);
    }

    @PostMapping
    @Operation(description = """
            가맹점 리스트 쿠폰 조회 API
            쿠폰을 조회 하고 싶은 가맹점들의 id 리스트가 필요합니다.
            가맹점 id, 2000/4000 쿠폰 개수 리스트를 반환합니다.
            """, summary = "가맹점 리스트 쿠폰 조회 API")
    public ResponseEntity<Response<List<CouponRes>>> getMyDonation(
            @RequestBody @Valid CouponListReq request
    ) {
        List<CouponRes> list = new ArrayList<>();
        for (Long l : request.franchiseIdList()) {
            CouponRes res1 = CouponRes.builder()
                    .franchiseId(l)
                    .coupon4(l * 5)
                    .coupon2(l * 2)
                    .build();
            list.add(res1);
        }
        SuccessResponse response = new SuccessResponse(
                COUPON_LIST_GET_SUCCESS,
                list
        );
        return Response.success(response);
    }

    @GetMapping("/my")
    @Operation(description = """
            사용자 보유 큐폰 조회 API
            사용자가 가진 모든 쿠폰 리스트가 반환됩니다.
            만약 query param으로 가맹점 id가 존재한다면 해당 가맹점의 쿠폰 리스트만 반환됩니다.
            가맹점 id, 가맹점 이름, 쿠폰 단위, 쿠폰 메시지 리스트를 반환합니다.
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
