package kr.co.olivepay.payment.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.payment.global.enums.ErrorCode;
import kr.co.olivepay.payment.global.properties.KafkaProperties;
import kr.co.olivepay.payment.global.response.Response;
import kr.co.olivepay.payment.service.PaymentEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailEventListener implements KafkaEventListener {

    @Value("${config.socket.TOPIC_PREFIX}")
    private String TOPIC_PREFIX;
    private final ObjectMapper objectMapper;
    private final PaymentEventService paymentEventService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @KafkaListener(topics = Topic.PAYMENT_FAIL, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        //레코드에서 key꺼내기
        String key = record.key();
        //레코드에서 value꺼내기
        String value = record.value();
        log.info("key : [{}], state : [{}]", key, "PAYMENT_FAIL");
        try {
            PaymentFailEvent paymentFailEvent
                    = objectMapper.readValue(value, PaymentFailEvent.class);
            paymentEventService.paymentFail(paymentFailEvent);
            log.info("PaymentFailEvent : {}", paymentFailEvent);
            log.info("결제 프로세스 종료 : [결제 실패]");
            String topic = TOPIC_PREFIX + "/" + key;

            Response<String> error
                    = Response.error(ErrorCode.PAYMENT_FAIL, paymentFailEvent.failReason());
            String payload
                    = objectMapper.writeValueAsString(error);

            simpMessagingTemplate.convertAndSend(topic, payload);
            acknowledgment.acknowledge();
            log.info("결제 프로세스 종료 : [결제 실패 전달 완료 : {}]", payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
