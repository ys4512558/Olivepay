package kr.co.olivepay.transaction.listener.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferSuccessEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponUsedSuccessEvent;
import kr.co.olivepay.transaction.PaymentSaga;
import kr.co.olivepay.transaction.listener.KafkaEventListener;
import kr.co.olivepay.transaction.properties.KafkaProperties;
import kr.co.olivepay.transaction.repository.PaymentSagaRepository;
import kr.co.olivepay.transaction.state.coupon.CouponTransferSuccess;
import kr.co.olivepay.transaction.state.coupon.CouponUsedSuccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponUsedSuccessListener implements KafkaEventListener {

    private final PaymentSagaRepository paymentSagaRepository;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = Topic.COUPON_USED_SUCCESS, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("COUPON_USED_SUCCESS 시작");
        String key = record.key();
        String value = record.value();
        try {
            CouponUsedSuccessEvent couponUsedSuccessEvent
                    = objectMapper.readValue(value, CouponUsedSuccessEvent.class);

            PaymentSaga paymentSaga = paymentSagaRepository.findById(key);
            paymentSaga.setStateAndOperate(new CouponUsedSuccess());
            paymentSagaRepository.deleteById(key);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("COUPON_USED_SUCCESS 종료");
    }
}
