package kr.co.olivepay.core.transaction.topic.event.account.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 계좌 잔액 조회 실패
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceCheckFailEvent {

    //계좌 잔액 실패 원인 (잔액 부족 등)
    private String failReason;
}
