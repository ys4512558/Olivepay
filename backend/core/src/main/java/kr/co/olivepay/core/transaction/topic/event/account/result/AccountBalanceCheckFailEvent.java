package kr.co.olivepay.core.transaction.topic.event.account.result;

import lombok.Builder;

/**
 * 계좌 잔액 조회 실패
 */
@Builder
public record AccountBalanceCheckFailEvent(
        //계좌 잔액 실패 원인 (잔액 부족 등)
        String failReason
) {

}
