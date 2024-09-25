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
    public AccountBalanceCheckRes checkAccountBalance(Long cardId, Long price) {
        Card card = cardTransactionService.getCardWithAccountById(cardId)
                                          .orElseThrow(() -> new AppException(CARD_NOT_EXIST));
        Account account = card.getAccount();
        if (account.getBalance() >= price) {
            return AccountBalanceCheckRes.builder()
                                         .isValid(true)
                                         .build();
        }
        return AccountBalanceCheckRes.builder()
                                     .isValid(false)
                                     .realCardNumber(card.getRealCardNumber())
                                     .build();
    }
}
