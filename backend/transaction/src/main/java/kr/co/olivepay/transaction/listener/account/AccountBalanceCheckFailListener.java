package kr.co.olivepay.transaction.listener.account;

import kr.co.olivepay.transaction.listener.KafkaEventListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBalanceCheckFailListener implements KafkaEventListener {

    @Override
    public void onMessage(ConsumerRecord<String, String> record) {

    }
}
