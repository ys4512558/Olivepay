package kr.co.olivepay.payment.service;

import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackEvent;
import kr.co.olivepay.payment.dto.res.PaymentApplyStateRes;

public interface PaymentEventService {

    /**
     * PaymentApplyEvent를 받아 실제 결제 처리
     *
     * @param event
     * @return : {@link PaymentApplyStateRes}
     */
    PaymentApplyStateRes paymentApply(PaymentApplyEvent event);

    /**
     * PaymentRollBackEvent를 받아 결제 정보 롤백 처리
     *
     * @param event
     * @return {@link Long} : paymentId
     */
    Long paymentRollBack(PaymentRollBackEvent event);

    /**
     * PaymentCompleteEvent를 받아 결제 완료 처리
     *
     * @param event
     */
    void paymentComplete(PaymentCompleteEvent event);

    /**
     * PaymentCompleteEvent를 받아 결제 취소 처리
     *
     * @param event
     */
    void paymentFail(PaymentFailEvent event);
}
