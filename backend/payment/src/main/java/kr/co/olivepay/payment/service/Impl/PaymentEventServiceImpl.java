package kr.co.olivepay.payment.service.Impl;

import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackEvent;
import kr.co.olivepay.payment.dto.res.PaymentApplyStateRes;
import kr.co.olivepay.payment.service.PaymentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentEventServiceImpl implements PaymentEventService {

    /**
     * 결제 적용 더미 서비스 로직
     *
     * @param event
     * @return
     */
    @Override
    public PaymentApplyStateRes paymentApply(PaymentApplyEvent event) {
        List<PaymentApplyHistory> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            PaymentApplyHistory paymentApplyHistory
                    = PaymentApplyHistory.builder()
                                         .paymentDetailId((long) i)
                                         .transactionUniqueNo(String.valueOf(30 + i))
                                         .build();
            list.add(paymentApplyHistory);
        }
        //성공 상태 객체 반환
        return PaymentApplyStateRes.builder()
                                   .isSuccess(false)
                                   .failReason("실패요!")
                                   .paymentApplyHistoryList(list)
                                   .build();
    }

    /**
     * 결제 롤백 더미 서비스 로직
     *
     * @param event
     * @return
     */
    @Override
    public Long paymentRollBack(PaymentRollBackEvent event) {
        Long paymentId = 1L;
        return paymentId;
    }

    /**
     * 결제 종료 : 결제 상태 완료로 변경
     *
     * @param event
     */
    @Override
    public void paymentComplete(PaymentCompleteEvent event) {

    }

    /**
     * 결제 종료 : 결제 상태 실패로 변경
     *
     * @param event
     */
    @Override
    public void paymentFail(PaymentFailEvent event) {

    }
}
