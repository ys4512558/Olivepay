package kr.co.olivepay.card.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.card.dto.req.CardRegisterReq;
import kr.co.olivepay.card.dto.req.CardSearchReq;
import kr.co.olivepay.card.dto.res.MyCardSearchRes;
import kr.co.olivepay.card.dto.res.TransactionCardSearchRes;
import kr.co.olivepay.card.global.enums.NoneResponse;
import kr.co.olivepay.card.global.response.Response;
import kr.co.olivepay.card.global.response.SuccessResponse;
import kr.co.olivepay.card.global.utils.CommonUtil;
import kr.co.olivepay.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    @Operation(
            summary = "카드 등록 API",
            description = "사용자의 실제 카드를 등록합니다."
    )
    public ResponseEntity<Response<NoneResponse>> registerCard(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid CardRegisterReq cardRegisterReq
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        SuccessResponse<NoneResponse> response = cardService.registerCard(memberId, cardRegisterReq);
        return Response.success(response);
    }

    @DeleteMapping("/{cardId}")
    @Operation(
            summary = "카드 삭제 API",
            description = "카드 ID를 통해 해당 카드를 삭제합니다."
    )
    public ResponseEntity<Response<NoneResponse>> deleteCard(
            @RequestHeader HttpHeaders headers,
            @PathVariable("cardId") Long cardId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        SuccessResponse<NoneResponse> response = cardService.deleteCard(memberId, cardId);
        return Response.success(response);
    }

    @GetMapping
    @Operation(
            summary = "사용자의 모든 카드 반환 API",
            description = "사용자의 모든 목록을 반환합니다. (꿈나무 카드 우선)"
    )
    public ResponseEntity<Response<List<MyCardSearchRes>>> getMyCardList(
            @RequestHeader HttpHeaders headers
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        SuccessResponse<List<MyCardSearchRes>> response = cardService.getMyCardList(memberId);
        return Response.success(response);
    }

    @PostMapping("/payment")
    @Operation(
            summary = "결제를 위한 카드 목록 반환 API (서비스간 호출)",
            description = "결제를 위한 카드 목록을 반환합니다. <br> " +
                    "1. 꿈나무 카드 무조건 반환 <br> " +
                    "2. isPublic = true -> 공용 기부금 카드 반환 <br> " +
                    "3. cardId != null -> 차액 결제 카드 반환 <br> " +
                    "반환 카드 수 : Min : 1개, Max : 3개"
    )
    public ResponseEntity<Response<List<TransactionCardSearchRes>>> getTransactionCardList(
            @RequestHeader HttpHeaders headers,
            @RequestBody(required = false) @Valid CardSearchReq cardSearchReq
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        SuccessResponse<List<TransactionCardSearchRes>> response = cardService.getTransactionCardList(memberId, cardSearchReq);
        return Response.success(response);
    }
}
