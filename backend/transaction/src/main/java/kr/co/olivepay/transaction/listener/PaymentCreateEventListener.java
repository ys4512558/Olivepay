package kr.co.olivepay.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentCreateEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.properties.KafkaProperties;
import kr.co.olivepay.transaction.publisher.TransactionEventPublisher;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.PaymentPending;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCreateEventListener implements KafkaEventListener {

    private final TransactionEventPublisher eventPublisher;
    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;
    private final PaymentPending paymentPending;

    @Override
    @KafkaListener(topics = Topic.PAYMENT_PENDING, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("PAYMENT_PENDING 시작");
        //레코드에서 key꺼내기
        String key = record.key();
        //레코드에서 value꺼내기
        String value = record.value();
        try {
            //ObjectMapper를 통해 PaymentCreateEvent로 컨버팅
            PaymentCreateEvent paymentCreateEvent = objectMapper.readValue(value, PaymentCreateEvent.class);
            log.info("PaymentCreateEvent : [{}]", paymentCreateEvent);
            //Event를 통해 Saga객체 초기화
            PaymentSaga paymentSaga = PaymentSaga.init(key, paymentCreateEvent, eventPublisher, paymentPending);
            //리포지토리에 저장
            paymentSagaRepository.save(key, paymentSaga);
            //다음 이벤트 수행
            paymentSaga.operate();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("PAYMENT_PENDING 종료");
    }
}
