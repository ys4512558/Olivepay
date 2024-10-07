package kr.co.olivepay.transaction.listener.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplySuccessEvent;
import kr.co.olivepay.transaction.PaymentDetailSaga;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.properties.KafkaProperties;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.payment.PaymentApplySuccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentApplySuccessListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;

    /**
     * 결제 적용 성공 이벤트 리스너
     *
     * @param record
     */
    @Override
    @KafkaListener(topics = Topic.PAYMENT_APPLY_SUCCESS, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("PAYMENT_APPLY_SUCCESS 시작");
        String key = record.key();
        String value = record.value();
        try {
            PaymentApplySuccessEvent paymentApplySuccessEvent
                    = objectMapper.readValue(value, PaymentApplySuccessEvent.class);
            List<PaymentApplyHistory> paymentApplyHistoryList
                    = paymentApplySuccessEvent.paymentApplyHistoryList();

            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            List<PaymentDetailSaga> paymentDetailSagaList = paymentSaga.getPaymentDetailSagaList();
            log.info("List<PaymentDetailSaga> : [{}]", paymentDetailSagaList);

            //결제 성공에 대한 transactionUniqueNo을 세팅
            for (PaymentApplyHistory paymentApplyHistory : paymentApplyHistoryList) {
                for (PaymentDetailSaga paymentDetailSaga : paymentDetailSagaList) {
                    if (Objects.equals(
                            paymentDetailSaga.getPaymentDetailId(),
                            paymentApplyHistory.paymentDetailId())
                    ) {
                        paymentDetailSaga.setTransactionUniqueNo(paymentApplyHistory.transactionUniqueNo());
                    }
                }
            }
            paymentSaga.setStateAndOperate(new PaymentApplySuccess());
            if (paymentSaga.getCouponUserId() == null) {
                paymentSagaRepository.deleteById(key);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("PAYMENT_APPLY_SUCCESS 종료");
    }
}
