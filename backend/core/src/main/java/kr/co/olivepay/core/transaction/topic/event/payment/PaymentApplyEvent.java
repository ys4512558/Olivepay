package kr.co.olivepay.core.transaction.topic.event.payment;

import lombok.Builder;

import java.util.List;

/**
 * 결제 적용 요청 이벤트
 */
@Builder
public record PaymentApplyEvent(
    //결제를 요청하는 사용자ID
    Long memberId,
    //결제할 가맹점 ID
    Long franchiseId,
    //결제에 사용될 정보
    List<PaymentDetailApplyEvent> paymentDetailApplyEventList
) {

}
