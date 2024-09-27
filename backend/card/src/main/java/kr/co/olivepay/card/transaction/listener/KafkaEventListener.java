package kr.co.olivepay.card.transaction.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaEventListener {

    void onMessage(ConsumerRecord<String, String> record);

}
