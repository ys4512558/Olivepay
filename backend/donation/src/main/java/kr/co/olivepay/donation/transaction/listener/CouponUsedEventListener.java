package kr.co.olivepay.donation.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.donation.dto.res.CouponUsedStateRes;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponUsedFailEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponUsedSuccessEvent;
import kr.co.olivepay.donation.service.DonationEventService;
import kr.co.olivepay.donation.transaction.publisher.TransactionEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponUsedEventListener implements KafkaEventListener {

    private final TransactionEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;
    private final DonationEventService donationEventService;

    @Override
    @KafkaListener(topics = Topic.COUPON_USED, groupId = "payment-orchestrator")
    public void onMessage(ConsumerRecord<String, String> record) {
        //레코드에서 key꺼내기
        String key = record.key();
        //레코드에서 value꺼내기
        String value = record.value();
        log.info("쿠폰 사용 처리 프로세스 시작");
        log.info("key : [{}], state : [{}]", key, "COUPON_USED");
        try {
            CouponUsedEvent couponUsedEvent = objectMapper.readValue(value, CouponUsedEvent.class);
            log.info("CouponUsedEvent : {}", couponUsedEvent);
            CouponUsedStateRes couponUsedStateRes = donationEventService.useCoupon(couponUsedEvent);
            if (couponUsedStateRes.isSuccess())
                publishSuccessEvent(key);
            else publishFailEvent(key);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("쿠폰 사용 처리 프로세스 종료");
    }

    private void publishFailEvent(String key) {
        eventPublisher.publishEvent(
                Topic.COUPON_USED_FAIL,
                key,
                new CouponUsedFailEvent("쿠폰 사용 실패")
        );
        log.info("COUPON_USED_FAIL 이벤트 발행");
    }

    private void publishSuccessEvent(String key) {
        eventPublisher.publishEvent(
                Topic.COUPON_USED_SUCCESS,
                key,
                new CouponUsedSuccessEvent()
        );
        log.info("COUPON_USED_SUCCESS 이벤트 발행");
    }
}
