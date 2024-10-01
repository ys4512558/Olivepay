package kr.co.olivepay.transaction.listener.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.account.result.AccountBalanceCheckFailEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.account.AccountBalanceCheckFail;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBalanceCheckFailListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;

    /**
     * 잔액 체크 실패 이벤트 리스너
     * 결제 프로세스 실패 이벤트 발행
     *
     * @param record
     */
    @Override
    @KafkaListener(topics = Topic.ACCOUNT_BALANCE_CHECK_FAIL, groupId = "payment-orchestrator")
    public void onMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        try {
            AccountBalanceCheckFailEvent accountBalanceCheckFailEvent
                    = objectMapper.readValue(value, AccountBalanceCheckFailEvent.class);

            String failReason = accountBalanceCheckFailEvent.failReason();

            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setFailReason(failReason);
            paymentSaga.setStateAndOperate(new AccountBalanceCheckFail());
            paymentSagaRepository.deleteById(key);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
