package kr.co.olivepay.payment.global.config;

import kr.co.olivepay.core.outbox.dto.req.DLQOutBoxReq;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.dlq.PaymentDLQEvent;
import kr.co.olivepay.payment.entity.PaymentDLQOutBox;
import kr.co.olivepay.payment.global.properties.KafkaProperties;
import kr.co.olivepay.payment.service.PaymentDLQOutBoxService;
import kr.co.olivepay.payment.transaction.publisher.TransactionEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.olivepay.payment.global.properties.KafkaProperties.KAFKA_GROUP_ID_CONFIG;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;
    private final PaymentDLQOutBoxService paymentDLQOutBoxService;
    private final TransactionEventPublisher eventPublisher;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        List<String> kafkaServers = kafkaProperties.getKAFKA_SERVERS();
        String bootstrapServer = String.join(",", kafkaServers);
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID_CONFIG);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        //재시도 전략 설정
        factory.setCommonErrorHandler(defaultErrorHandler());

        return factory;
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler((record, e) -> {
            log.error("에러 핸들링 처리 시작");
            String key = record.key()
                               .toString();
            String topic = record.topic();
            Object payload = record.value();
            //리플렉션 -> 나중에 역직렬화에 사용하기 위해 데이터 원래 타입을 문자열로 저장해두기
            String payloadType = payload.getClass()
                                        .getName();
            String errorMsg = e.getMessage();
            String errorType = e.getClass()
                                .getName();
            DLQOutBoxReq dlqOutBoxReq = DLQOutBoxReq.builder()
                                                    .key(key)
                                                    .topic(topic)
                                                    .payload(payload)
                                                    .payloadType(payloadType)
                                                    .errorMsg(errorMsg)
                                                    .errorType(errorType)
                                                    .build();
            log.error("Kafka 메시지 처리 실패, key={}, topic={}, payloadType={}, errorType={}",
                    key, topic, payloadType, errorType, e);

            PaymentDLQOutBox paymentDLQOutBox = paymentDLQOutBoxService.saveDLQOutBox(dlqOutBoxReq);
            PaymentDLQEvent paymentDLQEvent = PaymentDLQEvent.builder()
                                                             .key(key)
                                                             .topic(topic)
                                                             .payload(payload)
                                                             .payloadType(payloadType)
                                                             .errorMsg(errorMsg)
                                                             .errorType(errorType)
                                                             .build();
            eventPublisher.publishEvent(
                    Topic.PAYMENT_DLQ,
                    key,
                    paymentDLQEvent
            );
            paymentDLQOutBoxService.setSendDLQOutBox(paymentDLQOutBox.getId());
        }, new ExponentialBackOff(1000L, 2));
    }
}
