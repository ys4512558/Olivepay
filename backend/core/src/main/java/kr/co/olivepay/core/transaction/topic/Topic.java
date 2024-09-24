package kr.co.olivepay.core.transaction.topic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Topic {

    //결제 프로세스 시작
    public final static String PAYMENT_PENDING = "PAYMENT_PENDING";

    //잔액 확인
    public final static String ACCOUNT_BALANCE_CHECK = "ACCOUNT_BALANCE_CHECK";

    //결제 요청
    public final static String PAYMENT_APPLY = "PAYMENT_APPLY";

}

