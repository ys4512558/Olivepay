package kr.co.olivepay.card.service;

import kr.co.olivepay.card.dto.req.CardRegisterReq;
import kr.co.olivepay.card.dto.req.CardSearchReq;
import kr.co.olivepay.card.dto.res.MyCardSearchRes;
import kr.co.olivepay.card.dto.res.TransactionCardSearchRes;

import java.util.List;

public interface CardService {

    /**
     * 유저가 새로운 카드를 등록합니다.
     *
     * @param userId
     * @param cardRegisterReq
     */
    void registerCard(Long userId, String userKey, CardRegisterReq cardRegisterReq);

    /**
     * 유저가 자신의 카드를 삭제합니다.
     *
     * @param userId
     * @param cardId
     */
    void deleteCard(Long userId, Long cardId);

    /**
     * 사용자의 카드 목록을 반환합니다.
     *
     * @param userId
     * @return
     */
    List<MyCardSearchRes> getMyCardList(Long userId);

    /**
     * 결제를 위해 사용자의 꿈나무카드, 차액 결제 카드 및 공용 카드를 목록을 반환합니다.
     *
     * @param userId
     * @param cardSearchReq
     * @return
     */
    List<TransactionCardSearchRes> getTransactionCardList(Long userId, CardSearchReq cardSearchReq);

}
