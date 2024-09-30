package kr.co.olivepay.core.transaction.topic.event.account;

import lombok.Builder;

import java.util.List;

/**
 * 잔액 조회 요청 이벤트
 */
@Builder
public record AccountBalanceCheckEvent(

    //해당 계좌 소유주
    Long memberId,
    //유저의 핀테크 API Key
    String userKey,
    //계좌 잔액조회에 필요한 정보
    List<AccountBalanceDetailCheckEvent> accountBalanceDetailCheckEventList
) {

}
