package kr.co.olivepay.card.service;

import kr.co.olivepay.card.entity.Account;
import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.entity.CardCompany;
import kr.co.olivepay.card.global.enums.ErrorCode;
import kr.co.olivepay.card.global.handler.AppException;
import kr.co.olivepay.card.repository.AccountRepository;
import kr.co.olivepay.card.repository.CardCompanyRepository;
import kr.co.olivepay.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardTransactionService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CardCompanyRepository cardCompanyRepository;

    /**
     * 카드 등록
     *
     * @param account
     * @param card
     */
    @Transactional
    protected void registerCard(Account account, Card card) {
        accountRepository.save(account);
        cardRepository.save(card);
    }

    /**
     * 카드사 이름으로 카드사 객체 반환
     *
     * @param cardCompanyName
     * @return
     */
    @Transactional(readOnly = true)
    protected CardCompany getCardCompany(String cardCompanyName) {
        return cardCompanyRepository.findByName(cardCompanyName)
                                    .orElseThrow(() -> new AppException(ErrorCode.CARDCOMPANY_NOT_EXIST));
    }

    /**
     * 실제 카드번호로 카드객체 반환 Optional
     *
     * @param realCardNumber
     * @return
     */
    @Transactional(readOnly = true)
    protected Optional<Card> getCard(String realCardNumber) {
        Optional<Card> optionalCard = cardRepository.findByRealCardNumber(realCardNumber);
        return optionalCard;
    }

}
