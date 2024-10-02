package kr.co.olivepay.card.service;

import kr.co.olivepay.card.dto.res.AccountBalanceCheckRes;

public interface CardEventService {

    /**
     * 카드와 연결된 계좌의 잔액이 price보다 많은지 확인하는 메서드
     *
     * @param userKey
     * @param cardId
     * @param price
     * @return 잔액 >= price ? true : false {@link AccountBalanceCheckRes}
     */
    AccountBalanceCheckRes checkAccountBalance(String userKey, Long cardId, Long price);

}
