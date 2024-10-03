package kr.co.olivepay.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaEventListener {

    void onMessage(ConsumerRecord<String, String> record);

}
