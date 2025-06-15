package kr.co.olivepay.payment.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.payment.global.enums.NoneResponse;
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
public class PaymentCompleteEventListener implements KafkaEventListener {

    @Value("${config.socket.TOPIC_PREFIX}")
    private String TOPIC_PREFIX;
    private final ObjectMapper objectMapper;
    private final PaymentEventService paymentEventService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @KafkaListener(topics = Topic.PAYMENT_COMPLETE, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        //레코드에서 key꺼내기
        String key = record.key();
        //레코드에서 value꺼내기
        String value = record.value();
        log.info("key : [{}], state : [{}]", key, "PAYMENT_COMPLETE");
        try {
            PaymentCompleteEvent paymentCompleteEvent
                    = objectMapper.readValue(value, PaymentCompleteEvent.class);
            paymentEventService.paymentComplete(paymentCompleteEvent);
            log.info("PaymentCompleteEvent : {}", paymentCompleteEvent);
            log.info("결제 프로세스 종료 : [결제 성공]");
            String topic = TOPIC_PREFIX + "/" + key;

            Response<NoneResponse> success
             = new Response<>("SUCCESS", "PAYMENT_SUCCESS", "결제에 성공하였습니다.", NoneResponse.NONE);

            String payload
                    = objectMapper.writeValueAsString(success);

            simpMessagingTemplate.convertAndSend(topic, payload);
            acknowledgment.acknowledge();
            log.info("결제 프로세스 종료 : [결제 성공 전달 완료 : {}]", payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
