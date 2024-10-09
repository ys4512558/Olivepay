package kr.co.olivepay.common.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("ocr")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OCR {

    @Id
    private Long memberId;

    @Indexed
    private Integer count;

    @TimeToLive
    private Long ttl;

    @Builder
    public OCR(Long memberId, Integer count, Long ttl) {
        this.memberId = memberId;
        this.count = count;
        this.ttl = ttl;
    }

    public void incrementCount() {
        this.count += 1;
    }
}
