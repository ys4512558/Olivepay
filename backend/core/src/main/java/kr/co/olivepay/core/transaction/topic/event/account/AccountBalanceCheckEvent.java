package kr.co.olivepay.core.transaction.topic.event.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 잔액 조회 요청 이벤트
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceCheckEvent {

    //해당 계좌 소유주
    private Long memberId;
    //계좌 잔액조회에 필요한 정보
    private List<AccountBalanceDetailCheckEvent> accountBalanceDetailCheckEventList;

}
