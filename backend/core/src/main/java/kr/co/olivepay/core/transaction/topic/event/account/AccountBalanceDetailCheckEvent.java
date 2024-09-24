package kr.co.olivepay.core.transaction.topic.event.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 잔액 조회를 위한 상세 정보
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceDetailCheckEvent {

    //해당 결제에 사용될 카드ID
    private Long cardId;
    //해당 카드로 결제할 금액
    private Long price;

}
