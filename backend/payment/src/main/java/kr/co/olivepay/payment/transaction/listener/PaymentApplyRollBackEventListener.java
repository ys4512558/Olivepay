package kr.co.olivepay.payment.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplyRollBackCompleteEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackEvent;
import kr.co.olivepay.payment.global.properties.KafkaProperties;
import kr.co.olivepay.payment.service.PaymentEventService;
import kr.co.olivepay.payment.transaction.publisher.TransactionEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentApplyRollBackEventListener implements KafkaEventListener {

    private final TransactionEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;
    private final PaymentEventService paymentEventService;

    @Override
    @KafkaListener(topics = Topic.PAYMENT_APPLY_ROLLBACK, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        //레코드에서 key꺼내기
        String key = record.key();
        //레코드에서 value꺼내기
        String value = record.value();
        log.info("결제 프로세스 롤백 시작");
        log.info("key : [{}], state : [{}]", key, "PAYMENT_APPLY_ROLLBACK");
        try {
            PaymentRollBackEvent paymentRollBackEvent
                    = objectMapper.readValue(value, PaymentRollBackEvent.class);

            Long paymentId = paymentEventService.paymentRollBack(paymentRollBackEvent);
            publishPaymentApplyRollBackComplete(key, paymentId);
            log.info("PaymentRollBackEvent : {}", paymentRollBackEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("결제 프로세스 롤백 종료");
    }

    /**
     * 결제 롤백 처리 완료 이벤트 발행
     * @param key
     * @param paymentId
     */
    private void publishPaymentApplyRollBackComplete(String key, Long paymentId) {
        eventPublisher.publishEvent(
                Topic.PAYMENT_APPLY_ROLLBACK_COMPLETE,
                key,
                new PaymentApplyRollBackCompleteEvent(paymentId)
        );
    }
}
