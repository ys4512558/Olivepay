package kr.co.olivepay.transaction.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<SendResult<String, String>> publishEvent(final String topic, final String key, Object event) {
        try {
            return kafkaTemplate.send(topic, key, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
