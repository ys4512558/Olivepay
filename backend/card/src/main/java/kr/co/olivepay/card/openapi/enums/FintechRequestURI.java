package kr.co.olivepay.card.openapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FintechRequestURI {

    //계좌 생성 요청 URL
    CREATE_ACCOUNT("demandDeposit/createDemandDepositAccount"),
    //카드 생성 요청 URL
    CREATE_CARD("creditCard/createCreditCard"),
    //입금 URL
    DEPOSIT_ACCOUNT("demandDeposit/updateDemandDepositAccountDeposit");

    private final String uri;
}
