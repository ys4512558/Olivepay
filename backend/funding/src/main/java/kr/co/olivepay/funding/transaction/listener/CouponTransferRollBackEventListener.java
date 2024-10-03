package kr.co.olivepay.funding.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackCompleteEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackEvent;
import kr.co.olivepay.funding.global.properties.KafkaProperties;
import kr.co.olivepay.funding.service.FundingEventService;
import kr.co.olivepay.funding.transaction.publisher.TransactionEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponTransferRollBackEventListener implements KafkaEventListener {

    private final ObjectMapper objectMapper;
    private final TransactionEventPublisher eventPublisher;
    private final FundingEventService fundingEventService;

    @Override
    @KafkaListener(topics = Topic.COUPON_TRANSFER_ROLLBACK, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        log.info("쿠폰 잔액 이체 롤백 프로세스 시작");
        log.info("key : [{}], state : [{}]", key, "COUPON_TRANSFER_ROLLBACK");
        try {
            CouponTransferRollBackEvent couponTransferRollBackEvent
                    = objectMapper.readValue(value, CouponTransferRollBackEvent.class);

            Long couponUserID = fundingEventService.couponTransferRollBack(couponTransferRollBackEvent);
            publishRollBackComplete(key, couponUserID);
        } catch (JsonProcessingException e) {
            log.error("[쿠폰 잔액 이체 롤백 프로세스] : Json 파싱 에러 : {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("쿠폰 잔액 이체 롤백 프로세스 종료");
    }

    /**
     * 롤백 완료 이벤트 발행
     *
     * @param key
     * @param couponUserId
     */
    private void publishRollBackComplete(String key, Long couponUserId) {
        eventPublisher.publishEvent(
                Topic.COUPON_TRANSFER_ROLLBACK_COMPLETE,
                key,
                new CouponTransferRollBackCompleteEvent(couponUserId)
        );
    }
}
