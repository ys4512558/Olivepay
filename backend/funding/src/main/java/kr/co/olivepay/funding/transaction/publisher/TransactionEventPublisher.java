package kr.co.olivepay.funding.transaction.publisher;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface TransactionEventPublisher {

    CompletableFuture<SendResult<String, String>> publishEvent(String topic, String key, Object event);
}
