package kr.co.olivepay.card.service;

import kr.co.olivepay.card.dto.req.CardRegisterReq;
import kr.co.olivepay.card.dto.res.MyCardSearchRes;
import kr.co.olivepay.card.global.enums.NoneResponse;
import kr.co.olivepay.card.global.response.SuccessResponse;
import kr.co.olivepay.core.card.dto.req.CardSearchReq;
import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;

import java.util.List;

public interface CardService {

    /**
     * 유저가 새로운 카드를 등록합니다.
     *
     * @param memberId
     * @param cardRegisterReq
     * @return
     */
    SuccessResponse<NoneResponse> registerCard(Long memberId, CardRegisterReq cardRegisterReq);

    /**
     * 유저가 자신의 카드를 삭제합니다.
     *
     * @param memberId
     * @param cardId
     * @return
     */
    SuccessResponse<NoneResponse> deleteCard(Long memberId, Long cardId);

    /**
     * 사용자의 카드 목록을 반환합니다.
     *
     * @param memberId
     * @return
     */
    SuccessResponse<List<MyCardSearchRes>> getMyCardList(Long memberId);

    /**
     * 결제를 위해 사용자의 꿈나무카드, 차액 결제 카드 및 공용 카드를 목록을 반환합니다.
     *
     * @param memberId
     * @param cardSearchReq
     * @return
     */
    SuccessResponse<List<PaymentCardSearchRes>> getPaymentCardList(Long memberId, CardSearchReq cardSearchReq);

}
