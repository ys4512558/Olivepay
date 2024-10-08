package kr.co.olivepay.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.common.dto.req.SMSCheckReq;
import kr.co.olivepay.common.dto.req.SMSReq;
import kr.co.olivepay.common.global.enums.NoneResponse;
import kr.co.olivepay.common.global.response.Response;
import kr.co.olivepay.common.global.response.SuccessResponse;
import kr.co.olivepay.common.service.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commons/sms")
public class SmsController {
    private final SMSService smsService;
    @PostMapping
    @Operation(description = """
            휴대폰 인증번호 발송 API \n
            휴대폰 번호 11자리가 필요합니다.
            """
            , summary = "휴대폰 인증번호 발송 API")
    public ResponseEntity<Response<NoneResponse>> sendSMS(@Valid @RequestBody SMSReq request) {
        SuccessResponse<NoneResponse> response = smsService.sendSMS(request);
        return Response.success(response);
    }

    @PostMapping("/check")
    @Operation(description = """
            휴대폰 인증번호 검증 API \n
            휴대폰 번호와 인증 코드가 필요합니다.
            """
            , summary = "휴대폰 인증번호 발송 API")
    public ResponseEntity<Response<NoneResponse>> verifyCode(@Valid @RequestBody SMSCheckReq request) {
        SuccessResponse<NoneResponse> response = smsService.verifyCode(request);
        return Response.success(response);
    }

}
