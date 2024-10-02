package kr.co.olivepay.payment.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplyFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplySuccessEvent;
import kr.co.olivepay.payment.dto.res.PaymentApplyStateRes;
import kr.co.olivepay.payment.global.properties.KafkaProperties;
import kr.co.olivepay.payment.service.PaymentEventService;
import kr.co.olivepay.payment.transaction.publisher.TransactionEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentApplyEventListener implements KafkaEventListener {

    private final TransactionEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;
    private final PaymentEventService paymentEventService;

    @Override
    @KafkaListener(topics = Topic.PAYMENT_APPLY, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        //레코드에서 key꺼내기
        String key = record.key();
        //레코드에서 value꺼내기
        String value = record.value();
        log.info("결제 프로세스 적용 시작");
        log.info("key : [{}], state : [{}]", key, "PAYMENT_APPLY");
        try {
            PaymentApplyEvent paymentApplyEvent = objectMapper.readValue(value, PaymentApplyEvent.class);
            log.info("paymentApplyEvent : {}", paymentApplyEvent);
            PaymentApplyStateRes paymentApplyStateRes
                    = paymentEventService.paymentApply(paymentApplyEvent);
            if (paymentApplyStateRes.isSuccess()) {
                publishPaymentApplySuccessEvent(key, paymentApplyStateRes);
            } else {
                publishPaymentApplyFailEvent(key, paymentApplyStateRes);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("결제 프로세스 적용 종료");
    }

    /**
     * 결제 실패 이벤트 발행
     *
     * @param key
     */
    private void publishPaymentApplyFailEvent(String key, PaymentApplyStateRes paymentApplyStateRes) {
        String failReason = paymentApplyStateRes.failReason();
        List<PaymentApplyHistory> paymentApplyHistoryList
                = paymentApplyStateRes.paymentApplyHistoryList();
        eventPublisher.publishEvent(
                Topic.PAYMENT_APPLY_FAIL,
                key,
                new PaymentApplyFailEvent(failReason, paymentApplyHistoryList)
        );
    }

    private void publishPaymentApplySuccessEvent(String key, PaymentApplyStateRes paymentApplyStateRes) {
        List<PaymentApplyHistory> paymentApplyHistoryList
                = paymentApplyStateRes.paymentApplyHistoryList();
        eventPublisher.publishEvent(
                Topic.PAYMENT_APPLY_SUCCESS,
                key,
                new PaymentApplySuccessEvent(paymentApplyHistoryList)
        );
    }
}
