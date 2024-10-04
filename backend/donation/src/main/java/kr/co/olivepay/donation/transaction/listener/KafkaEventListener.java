package kr.co.olivepay.donation.transaction.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaEventListener {

    void onMessage(ConsumerRecord<String, String> record);

}
