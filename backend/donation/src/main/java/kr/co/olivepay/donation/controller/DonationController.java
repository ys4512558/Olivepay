package kr.co.olivepay.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import kr.co.olivepay.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donations")
public class DonationController {
    private final DonationService donationService;
    @PostMapping
    @Operation(description = """
            후원하기 API \n
            이메일, 전화번호, 총 금액, 가맹점 id, 후원메세지, 2000/4000 쿠폰 갯수, 계좌번호가 필요합니다. \n
            """, summary = "후원하기 API")
    public ResponseEntity<Response<NoneResponse>> donate(
            @RequestBody @Valid DonationReq request
    ) {
        SuccessResponse<NoneResponse> response = donationService.donate(request);
        return Response.success(response);
    }
}
