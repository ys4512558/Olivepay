package kr.co.olivepay.card.controller;

import kr.co.olivepay.card.global.enums.NoneResponse;
import kr.co.olivepay.card.global.enums.SuccessCode;
import kr.co.olivepay.card.global.response.Response;
import kr.co.olivepay.card.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    @GetMapping
    public ResponseEntity<Response<NoneResponse>> apiTest() {
        return Response.success(new SuccessResponse<NoneResponse>(SuccessCode.SUCCESS, NoneResponse.NONE));
    }

}
