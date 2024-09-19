package kr.co.olivepay.donation.controller;

import com.sun.net.httpserver.Authenticator;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.enums.SuccessCode;
import kr.co.olivepay.donation.global.response.Response;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<Response<NoneResponse>> test() {
        return Response.success(new SuccessResponse<>(SuccessCode.SUCCESS,NoneResponse.NONE));
    }
}
