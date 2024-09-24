package kr.co.olivepay.card.service;

import kr.co.olivepay.card.dto.res.AccountBalanceCheckRes;

public interface CardEventService {

    AccountBalanceCheckRes checkAccountBalance(Long cardId, Long price);

}
