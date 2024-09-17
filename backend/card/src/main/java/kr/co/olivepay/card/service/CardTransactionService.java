package kr.co.olivepay.card.service;

import kr.co.olivepay.card.dto.req.CardSearchReq;
import kr.co.olivepay.card.entity.Account;
import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.entity.CardCompany;

import java.util.List;
import java.util.Optional;

public interface CardTransactionService {

    Card registerCard(Account account, Card card);

    CardCompany getCardCompany(String cardCompanyName);

    Optional<Card> getCard(String realCardNumber);

    void deleteCard(Long memberId, Long cardId);

    List<Card> getMyCardList(Long memberId);

    List<Card> getTransactionCardList(Long memberId, CardSearchReq cardSearchReq);

}