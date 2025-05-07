package kr.co.olivepay.core.transaction.topic.event.dlq;

import lombok.Builder;

/**
 * 결제 과정에서 발생한 DLQ
 */
@Builder
public record PaymentDLQEvent(
        String key, //Kafka 이벤트 키
        String topic, //토픽 종류
        Object payload, //데이터
        String payloadType, //데이터 타입명
        String errorMsg,
        String errorType
) {

}
