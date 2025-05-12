package kr.co.olivepay.transaction.listener.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplyRollBackCompleteEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.properties.KafkaProperties;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.payment.PaymentApplyRollBackComplete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 결제 적용 롤백 적용 이벤트 리스너 (롤백 완료)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentApplyRollBackedListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;
    private final PaymentApplyRollBackComplete paymentApplyRollBackComplete;

    @Override
    @KafkaListener(topics = Topic.PAYMENT_APPLY_ROLLBACK_COMPLETE, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("PAYMENT_APPLY_ROLLBACK_COMPLETE 시작");
        String key = record.key();
        String value = record.value();
        try {
            PaymentApplyRollBackCompleteEvent paymentApplyRollBackCompleteEvent
                    = objectMapper.readValue(value, PaymentApplyRollBackCompleteEvent.class);
            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setStateAndOperate(paymentApplyRollBackComplete);
            paymentSagaRepository.deleteById(key);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("PAYMENT_APPLY_ROLLBACK_COMPLETE 종료");
    }
}
