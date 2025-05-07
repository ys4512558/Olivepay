package kr.co.olivepay.payment.entity;


import jakarta.persistence.*;
import kr.co.olivepay.payment.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDLQOutBox extends BaseEntity {
    @Id
    @Column(name = "dlq_outbox_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "event_key", nullable = false, unique = true)
    String eventkey; //Kafka 이벤트 키

    @Column(nullable = false)
    String topic; //토픽 종류

    @Column(columnDefinition = "json")
    Object payload; //데이터

    @Column(nullable = false)
    String payloadType;//데이터 타입명

    @Column(nullable = false)
    String errorMsg;

    @Column(nullable = false)
    String errorType;

    @Builder
    public PaymentDLQOutBox(Long id, String eventkey, String topic, Object payload, String payloadType, String errorMsg, String errorType) {
        this.id = id;
        this.eventkey = eventkey;
        this.topic = topic;
        this.payload = payload;
        this.payloadType = payloadType;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
    }
}
