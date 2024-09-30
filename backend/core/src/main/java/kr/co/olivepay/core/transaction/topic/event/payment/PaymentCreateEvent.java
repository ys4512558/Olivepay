package kr.co.olivepay.core.transaction.topic.event.payment;

import lombok.Builder;

import java.util.List;

/**
 * 결제 생성 이벤트
 */
@Builder
public record PaymentCreateEvent(
        //결제ID
        Long paymentId,
        //결제를 요청한 사용자
        Long memberId,
        //유저의 API key
        String userKey,
        //결제할 가맹점
        Long franchiseId,
        //쿠폰이 있다면 쿠폰 ID,
        Long couponId,
        //결제에 사용될 결제 정보
        List<PaymentDetailCreateEvent> paymentDetailCreateEventList
) {

}
