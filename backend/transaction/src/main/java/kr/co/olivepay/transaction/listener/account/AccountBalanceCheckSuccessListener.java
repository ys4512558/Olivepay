package kr.co.olivepay.transaction.listener.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.account.AccountBalanceCheckSuccess;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBalanceCheckSuccessListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;

    /**
     * 계좌 잔액 체크 성공
     * @param record
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        try {
            String key = record.key();
            String value = record.value();
            AccountBalanceCheckSuccess accountBalanceCheckSuccess
                    = objectMapper.readValue(value, AccountBalanceCheckSuccess.class);
            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setState(new AccountBalanceCheckSuccess());
            paymentSaga.operate();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
