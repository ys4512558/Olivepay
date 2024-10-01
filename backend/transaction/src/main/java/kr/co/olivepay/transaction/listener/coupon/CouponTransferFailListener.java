package kr.co.olivepay.transaction.listener.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferFailEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.coupon.CouponTransferFail;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponTransferFailListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = Topic.COUPON_TRANSFER_FAIL, groupId = "payment-orchestrator")
    public void onMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        try {
            CouponTransferFailEvent couponTransferFailEvent
                    = objectMapper.readValue(value, CouponTransferFailEvent.class);

            String failReason = couponTransferFailEvent.failReason();
            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setFailReason(failReason);
            paymentSaga.setStateAndOperate(new CouponTransferFail());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
