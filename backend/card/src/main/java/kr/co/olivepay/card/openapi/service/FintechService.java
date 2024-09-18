package kr.co.olivepay.card.openapi.service;

import kr.co.olivepay.card.openapi.dto.res.account.AccountRec;
import kr.co.olivepay.card.openapi.dto.res.card.CardRec;

public interface FintechService {

    /**
     * 계좌 생성
     *
     * @param userKey: 유저 키
     * @return 생성된 계좌 반환 (accountNo)
     */
    AccountRec createAccount(String userKey);

    /**
     * 카드 생성
     *
     * @param userKey             : 유저 키
     * @param withdrawalAccountNo : 출금 계좌
     * @param cardCompanyName     : 카드사 이름
     * @return 생성된 카드 번호 반환 (cardNo)
     */
    CardRec createCard(String userKey, String withdrawalAccountNo, String cardCompanyName);
}
