package kr.co.olivepay.transaction.listener.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplySuccessEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.payment.PaymentApplySuccess;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentApplySuccessListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;

    /**
     * 결제 적용 성공 이벤트 리스너
     *
     * @param record
     */
    @Override
    @KafkaListener(topics = Topic.PAYMENT_APPLY_SUCCESS, groupId = "payment-orchestrator")
    public void onMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        try {
            PaymentApplySuccessEvent paymentApplySuccessEvent
                    = objectMapper.readValue(value, PaymentApplySuccessEvent.class);

            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setStateAndOperate(new PaymentApplySuccess());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
