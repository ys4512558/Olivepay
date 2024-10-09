package kr.co.olivepay.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.core.common.dto.req.EmailReq;
import kr.co.olivepay.common.global.enums.NoneResponse;
import kr.co.olivepay.common.global.response.Response;
import kr.co.olivepay.common.global.response.SuccessResponse;
import kr.co.olivepay.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commons/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    @Operation(description = """
            이메일 보내기 API (개발용)  \n
            이메일 보낼 대상과 후원 내역이 필요합니다.
            """
            , summary = "이메일 보내기 API (개발용) ")
    public ResponseEntity<Response<NoneResponse>> test(@Valid @RequestBody EmailReq request) {
        SuccessResponse<NoneResponse> response = emailService.send(request);
        return Response.success(response);
    }
}
