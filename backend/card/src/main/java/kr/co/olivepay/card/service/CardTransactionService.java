package kr.co.olivepay.card.service;

import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.entity.CardCompany;
import kr.co.olivepay.core.card.dto.req.CardSearchReq;

import java.util.List;
import java.util.Optional;

public interface CardTransactionService {

    Card registerCard(Card card);

    CardCompany getCardCompany(String cardCompanyName);

    Optional<Card> getCard(String realCardNumber);

    Optional<Card> getDefaultCard(Long memberId);

    void deleteCard(Long memberId, Long cardId);

    List<Card> getMyCardList(Long memberId);

    List<Card> getPaymentCardList(Long memberId, CardSearchReq cardSearchReq);

    Optional<Card> getCardWithAccountById(Long cardId);
}