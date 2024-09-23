package kr.co.olivepay.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.donation.dto.req.DonationMyReq;
import kr.co.olivepay.donation.dto.res.DonationMyRes;
import kr.co.olivepay.donation.dto.res.DonationTotalRes;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static kr.co.olivepay.donation.global.enums.SuccessCode.DONATION_MY_SUCCESS;
import static kr.co.olivepay.donation.global.enums.SuccessCode.DONATION_TOTAL_SUCCESS;

@RestController
@RequestMapping("/donations/donors")
public class DonorController {

    @GetMapping
    @Operation(description = """
            후원 통합 현황 조회 API
            총 후원 금액과 지원한 끼니 내역 수를 반환합니다.
            """, summary = "후원 통합 현항 조회 API")
    public ResponseEntity<Response<DonationTotalRes>> getDonationTotal() {
        SuccessResponse response = new SuccessResponse(
                DONATION_TOTAL_SUCCESS,
                DonationTotalRes.builder()
                        .total(3820000L)
                        .mealCount(650L)
                        .build()
        );
        return Response.success(response);
    }

    @GetMapping("my")
    @Operation(description = """
            후원자의 후원 내역 상세 조회 API
            후원자의 이메일, 전화번호가 필요합니다.
            """, summary = "후원자의 후원 내역 상세 조회 API")
    public ResponseEntity<Response<List<DonationMyRes>>> getMyDonation(
            @RequestBody @Valid DonationMyReq request
    ) {
        List<DonationMyRes> list = new ArrayList<>();
        DonationMyRes res1 = DonationMyRes.builder()
                .date(new Date())
                .franchiseId(1L)
                .name("가맹점1")
                .address("서울시 송파구")
                .money(50000)
                .build();
        DonationMyRes res2 = DonationMyRes.builder()
                .date(new Date())
                .franchiseId(2L)
                .name("가맹점2")
                .address("서울시 강남구")
                .money(100000)
                .build();
        list.add(res1);
        list.add(res2);
        SuccessResponse response = new SuccessResponse(
                DONATION_MY_SUCCESS,
                list
        );
        return Response.success(response);
    }

}
