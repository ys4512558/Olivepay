package kr.co.olivepay.funding.transaction.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTransactionEventPublisher implements TransactionEventPublisher{

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 카프카 이벤트 발행 메서드
     * @param topic
     * @param key
     * @param event
     * @return
     */
    @Override
    public CompletableFuture<SendResult<String, String>> publishEvent(
            final String topic,
            final String key,
            final Object event) {
        try {
            return kafkaTemplate.send(topic, key, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
