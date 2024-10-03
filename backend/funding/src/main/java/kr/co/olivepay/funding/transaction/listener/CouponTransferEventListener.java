package kr.co.olivepay.funding.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponTransferEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferFailEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferSuccessEvent;
import kr.co.olivepay.funding.dto.res.CouponTransferStateRes;
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
public class CouponTransferEventListener implements KafkaEventListener {

    private final ObjectMapper objectMapper;
    private final TransactionEventPublisher eventPublisher;
    private final FundingEventService fundingEventService;

    @Override
    @KafkaListener(topics = Topic.COUPON_TRANSFER, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        log.info("쿠폰 잔액 이체 프로세스 시작");
        log.info("key : [{}], state : [{}]", key, "COUPON_TRANSFER");
        try {
            CouponTransferEvent couponTransferEvent
                    = objectMapper.readValue(value, CouponTransferEvent.class);
            log.info("CouponTransferStateRes : {}", couponTransferEvent);
            //서비스 로직 수행
            CouponTransferStateRes couponTransferStateRes
                    = fundingEventService.couponTransfer(couponTransferEvent);
            if (couponTransferStateRes.isSuccess()) {
                publishCouponTransferSuccessEvent(key);
            } else {
                String failReason = couponTransferStateRes.failReason();
                publishCouponTransferFailEvent(key, failReason);
            }
        } catch (JsonProcessingException e) {
            log.error("[쿠폰 잔액 이체 프로세스] : Json 파싱 에러 : {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("쿠폰 잔액 이체 프로세스 종료");
    }

    /**
     * 쿠폰 잔액 이체 성공 이벤트 발행
     *
     * @param key
     */
    private void publishCouponTransferSuccessEvent(String key) {
        eventPublisher.publishEvent(
                Topic.COUPON_TRANSFER_SUCCESS,
                key,
                new CouponTransferSuccessEvent()
        );
    }

    /**
     * 쿠폰 잔액 이체 실패 이벤트 발행
     *
     * @param key
     * @param failReason
     */
    private void publishCouponTransferFailEvent(String key, String failReason) {
        eventPublisher.publishEvent(
                Topic.COUPON_TRANSFER_FAIL,
                key,
                new CouponTransferFailEvent(failReason)
        );
    }
}
