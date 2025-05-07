package kr.co.olivepay.core.outbox.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class OutBoxReq {
    private String key; //Kafka 이벤트 키
    private String topic; //토픽 종류
    private Object payload; //데이터
    private String payloadType; //데이터 타입명
}
