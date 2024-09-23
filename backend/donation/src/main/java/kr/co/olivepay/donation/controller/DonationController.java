package kr.co.olivepay.donation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.enums.SuccessCode;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kr.co.olivepay.donation.global.enums.NoneResponse.*;
import static kr.co.olivepay.donation.global.enums.SuccessCode.*;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @PostMapping
    @Operation(description = """
            후원하기 API \n
            이메일, 전화번호, 총 금액, 가맹점 id, 후원메세지, 2000/4000 쿠폰 갯수, 계좌번호가 필요합니다. \n
            """, summary = "후원하기 API")
    public ResponseEntity<Response<NoneResponse>> donation(
            @RequestBody @Valid DonationReq request
    ) {
        return Response.success(new SuccessResponse<>(DONATION_SUCCESS, NONE));
    }
}
