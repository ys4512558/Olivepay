package kr.co.olivepay.card.openapi.service;

import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountBalanceRec;
import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountDepositRec;
import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountRec;
import kr.co.olivepay.card.openapi.dto.res.card.rec.CardRec;

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

    /**
     * 계좌 입금
     *
     * @param userKey            : 유저 키
     * @param accountNo          : 입금 계좌 번호
     * @param transactionBalance : 입금액
     * @param transactionSummary : 입금 메모
     * @return
     */
    AccountDepositRec depositAccount(String userKey, String accountNo, String transactionBalance, String transactionSummary);

    /**
     * 계좌 잔액 조회
     * @param userKey
     * @param accountNo
     * @return
     */
    AccountBalanceRec getAccountBalance(String userKey, String accountNo);
}
