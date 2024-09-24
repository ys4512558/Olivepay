package kr.co.olivepay.core.transaction.topic.event.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 결제 적용 요청 이벤트
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApplyEvent {

    //결제를 요청하는 사용자ID
    private Long memberId;
    //결제할 가맹점 ID
    private Long franchiseId;
    //결제에 사용될 정보
    private List<PaymentDetailApplyEvent> paymentDetailApplyEventList;

}
