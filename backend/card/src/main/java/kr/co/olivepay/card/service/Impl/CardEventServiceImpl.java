package kr.co.olivepay.card.service.Impl;

import kr.co.olivepay.card.dto.res.AccountBalanceCheckRes;
import kr.co.olivepay.card.entity.Account;
import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.global.handler.AppException;
import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountBalanceRec;
import kr.co.olivepay.card.openapi.service.FintechService;
import kr.co.olivepay.card.service.CardEventService;
import kr.co.olivepay.card.service.CardTransactionService;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.card.global.enums.ErrorCode.CARD_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class CardEventServiceImpl implements CardEventService {

    private final CardTransactionService cardTransactionService;
    private final FintechService fintechService;

    /**
     * 카드와 연결된 계좌의 잔액이 price보다 많은지 확인하는 메서드
     *
     * @param userKey
     * @param cardId
     * @param price
     * @return 잔액 >= price ? true : false {@link AccountBalanceCheckRes}
     */
    @Override
    public AccountBalanceCheckRes checkAccountBalance(
            final String userKey,
            final Long cardId,
            final Long price
    ) {
        Card card = cardTransactionService.getCardWithAccountById(cardId)
                                          .orElseThrow(() -> new AppException(CARD_NOT_EXIST));
        Account account = card.getAccount();
        AccountBalanceRec accountBalance = fintechService.getAccountBalance(userKey, account.getAccountNumber());
        
        long balance = Long.parseLong(accountBalance.getAccountBalance());
        AccountBalanceCheckRes.AccountBalanceCheckResBuilder builder = AccountBalanceCheckRes.builder();

        if (balance >= price) {
            builder.isValid(true);
        } else {
            builder.isValid(false);
            String failReason = getFailReason(card);
            builder.failReason(failReason);
        }
        return builder.build();
    }

    /**
     * 카드에 대해 실패 이유 스트링을 만들어 반환
     * @param card
     * @return 카드에 대한 실패 이유 문자열
     */
    private String getFailReason(Card card) {
        CardType cardType = card.getCardType();
        StringBuilder failReason = new StringBuilder();
        failReason.append("[");
        switch (cardType) {
            case COUPON:
                failReason.append("쿠폰 카드");
                break;
            case DREAMTREE:
                failReason.append("꿈나무 카드");
                break;
            default: //차액 카드
                failReason.append(card.getRealCardNumber() + "카드");
                break;
        }
        failReason.append("] 잔액 부족");
        return failReason.toString();
    }
}
