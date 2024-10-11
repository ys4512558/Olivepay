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
     * @param depositTransactionSummary 거래 요약 내용(입금 계좌)
     * @param withdrawalTransactionSummary 거래 요약 내용(출금 계좌)
     * @return
     */
    List<AccountTransferRec> transferAccount(String userKey, String depositAccountNo, String transactionBalance, String withdrawalAccountNo, String depositTransactionSummary, String withdrawalTransactionSummary);

}
