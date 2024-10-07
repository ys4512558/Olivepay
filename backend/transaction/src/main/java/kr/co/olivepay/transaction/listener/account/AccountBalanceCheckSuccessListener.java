package kr.co.olivepay.transaction.listener.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.properties.KafkaProperties;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.account.AccountBalanceCheckSuccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
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
    @KafkaListener(topics = Topic.ACCOUNT_BALANCE_CHECK_SUCCESS, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("ACCOUNT_BALANCE_CHECK_SUCCESS 시작");
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
        log.info("ACCOUNT_BALANCE_CHECK_SUCCESS 종료");
    }
}
