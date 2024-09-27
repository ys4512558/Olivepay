package kr.co.olivepay.card.service.Impl;

import kr.co.olivepay.card.dto.res.AccountBalanceCheckRes;
import kr.co.olivepay.card.entity.Account;
import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.global.handler.AppException;
import kr.co.olivepay.card.service.CardEventService;
import kr.co.olivepay.card.service.CardTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.card.global.enums.ErrorCode.CARD_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class CardEventServiceImpl implements CardEventService {

    private final CardTransactionService cardTransactionService;


    /**
     * 카드와 연결된 계좌의 잔액이 price보다 많은지 확인하는 메서드
     * @param cardId
     * @param price
     * @return 잔액 >= price ? true : false
     */
    @Override
    public AccountBalanceCheckRes checkAccountBalance(final Long cardId, final Long price) {
        Card card = cardTransactionService.getCardWithAccountById(cardId)
                                          .orElseThrow(() -> new AppException(CARD_NOT_EXIST));
        Account account = card.getAccount();
        //TODO: 계좌 잔액 체크 API 호출 및 로직 작성
        return null;
    }
}
