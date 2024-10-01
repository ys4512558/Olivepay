package kr.co.olivepay.funding.openapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FintechRequestURI {
    //계좌 이체
    TRANSFER_ACCOUNT("demandDeposit/updateDemandDepositAccountTransfer");

    private final String uri;
}
