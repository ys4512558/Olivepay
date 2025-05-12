package kr.co.olivepay.transaction.listener.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackCompleteEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentApplyRollBackCompleteEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.properties.KafkaProperties;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.coupon.CouponTransferRollBackComplete;
import kr.co.olivepay.transaction.state.payment.PaymentApplyRollBackComplete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 결제 적용 롤백 적용 이벤트 리스너 (롤백 완료)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CouponTransferRollBackedListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;
    private final CouponTransferRollBackComplete couponTransferRollBackComplete;

    @Override
    @KafkaListener(topics = Topic.COUPON_TRANSFER_ROLLBACK_COMPLETE, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("COUPON_TRANSFER_ROLLBACK_COMPLETE 시작");
        String key = record.key();
        String value = record.value();
        try {
            CouponTransferRollBackCompleteEvent couponTransferRollBackCompleteEvent
                    = objectMapper.readValue(value, CouponTransferRollBackCompleteEvent.class);
            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setStateAndOperate(couponTransferRollBackComplete);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("COUPON_TRANSFER_ROLLBACK_COMPLETE 종료");
    }
}
