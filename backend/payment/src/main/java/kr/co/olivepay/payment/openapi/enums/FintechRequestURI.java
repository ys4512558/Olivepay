package kr.co.olivepay.payment.openapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FintechRequestURI {

    //카드 결제
    CREATE_CARD_TRANSACTION("creditCard/createCreditCardTransaction"),
    //카드 결제 취소
    CANCEL_CARD_TRANSACTION("creditCard/deleteTransaction");

    private final String uri;
}
