package kr.co.olivepay.transaction.listener.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.account.AccountBalanceCheckSuccess;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBalanceCheckSuccessListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;

    /**
     * 계좌 잔액 체크 성공
     *
     * @param record
     */
    @Override
    @KafkaListener(topics = Topic.ACCOUNT_BALANCE_CHECK_SUCCESS, groupId = "payment-orchestrator")
    public void onMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        try {
            AccountBalanceCheckSuccess accountBalanceCheckSuccess
                    = objectMapper.readValue(value, AccountBalanceCheckSuccess.class);

            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setStateAndOperate(new AccountBalanceCheckSuccess());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
