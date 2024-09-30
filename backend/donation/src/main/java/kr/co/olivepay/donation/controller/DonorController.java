package kr.co.olivepay.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.donation.dto.req.DonationMyReq;
import kr.co.olivepay.donation.dto.res.DonationMyRes;
import kr.co.olivepay.donation.dto.res.DonationTotalRes;
import kr.co.olivepay.core.global.dto.res.PageResponse;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import kr.co.olivepay.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donations/donors")
public class DonorController {

    private final DonationService donationService;

    @GetMapping
    @Operation(description = """
            후원 통합 현황 조회 API \n
            총 후원 금액과 지원한 끼니 내역 수를 반환합니다. \n
            """, summary = "후원 통합 현항 조회 API")
    public ResponseEntity<Response<DonationTotalRes>> getDonationTotal() {
        SuccessResponse<DonationTotalRes> response = donationService.getDonationTotal();
        return Response.success(response);
    }

    @PostMapping("my")
    @Operation(description = """
            후원자의 후원 내역 상세 조회 API \n
            후원자의 이메일, 전화번호가 필요합니다. \n
            """, summary = "후원자의 후원 내역 상세 조회 API")
    public ResponseEntity<Response<PageResponse<List<DonationMyRes>>>> getMyDonation(
            @RequestBody @Valid DonationMyReq request,
            @RequestParam(defaultValue = "0") Long nextIndex
    ) {
        SuccessResponse<PageResponse<List<DonationMyRes>>> response = donationService.getMyDonation(request, nextIndex);
        return Response.success(response);
    }

}
