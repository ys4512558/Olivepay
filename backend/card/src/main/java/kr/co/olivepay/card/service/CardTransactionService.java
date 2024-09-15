package kr.co.olivepay.card.service;

import jakarta.annotation.PostConstruct;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardTransactionService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CardCompanyRepository cardCompanyRepository;

    /**
     * 카드 등록
     * @param account
     * @param card
     * @return 등록된 카드 반환
     */
    @Transactional
    protected Card registerCard(Account account, Card card) {
        accountRepository.save(account);
        return cardRepository.save(card);
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

    /**
     * 카드 삭제 로직
     * 삭제된 레코드가 0개이면 카드 존재 X 예외처리
     *
     * @param memberId
     * @param cardId
     */
    @Transactional
    public void deleteCard(Long memberId, Long cardId) {
        int cnt = cardRepository.deleteByIdAndMemberId(cardId, memberId);
        if (cnt == 0) {
            throw new AppException(ErrorCode.CARD_NOT_EXIST);
        }
    }

    /**
     * 카드사 초기 생성을 위한 작업
     */
    @PostConstruct
    @Transactional
    public void init() {
        List<CardCompany> cardCompanyList = cardCompanyRepository.findAll();
        if (!cardCompanyList.isEmpty()) {
            return;
        }
        String[] names = {
                "KB국민카드", "삼성카드", "롯데카드", "우리카드",
                "신한카드", "현대카드", "BC카드", "NH농협카드",
                "하나카드", "IBK기업은행"
        };
        for (int i = 0; i < 10; i++) {
            CardCompany cardCompany = CardCompany.builder()
                                           .name(names[i])
                                           .build();
            cardCompanyRepository.save(cardCompany);
        }
    }
}
