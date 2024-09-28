package kr.co.olivepay.transaction.listener.payment;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentApplyFailListener implements KafkaEventListener {

    /**
     * 결제 적용 실패 이벤트 리스너
     *
     * @param record
     */
    @Override
    @KafkaListener(topics = Topic.PAYMENT_APPLY_FAIL, groupId = "payment-orchestrator")
    public void onMessage(ConsumerRecord<String, String> record) {

    }
}
