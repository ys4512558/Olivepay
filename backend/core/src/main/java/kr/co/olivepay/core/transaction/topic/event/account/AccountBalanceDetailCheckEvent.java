package kr.co.olivepay.core.transaction.topic.event.account;

import lombok.Builder;

/**
 * 잔액 조회를 위한 상세 정보
 */
@Builder
public record AccountBalanceDetailCheckEvent(
        //해당 결제에 사용될 카드ID
        Long cardId,
        //해당 카드로 결제할 금액
        Long price
) {

}
