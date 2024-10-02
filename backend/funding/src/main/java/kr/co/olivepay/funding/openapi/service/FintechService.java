package kr.co.olivepay.funding.openapi.service;

import java.util.List;

import kr.co.olivepay.funding.openapi.dto.res.account.rec.AccountTransferRec;

public interface FintechService {

    /**
     * 계좌 이체
     * @param userKey
     * @param depositAccountNo 입금 계좌 번호
     * @param transactionBalance 금액
     * @param withdrawalAccountNo 출금 계좌 번호
     * @param depositTransactionSummary
     * @param withdrawalTransactionSummary
     * @return
     */
    List<AccountTransferRec> transferAccount(String userKey, String depositAccountNo, String transactionBalance, String withdrawalAccountNo, String depositTransactionSummary, String withdrawalTransactionSummary);

}
