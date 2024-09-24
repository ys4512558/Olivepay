package kr.co.olivepay.core.transaction.topic.event.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 결제 생성 이벤트
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateEvent {

    //결제ID
    private Long paymentId;
    //결제를 요청한 사용자
    private Long memberId;
    //결제할 가맹점
    private Long franchiseId;
    //결제에 사용될 결제 정보
    private List<PaymentDetailCreateEvent> paymentDetailCreateEventList;

}
