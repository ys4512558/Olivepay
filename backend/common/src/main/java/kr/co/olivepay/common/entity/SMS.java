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
@RedisHash("sms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SMS {

    @Id
    private String phone;
    @Indexed
    private String code;
    @TimeToLive
    private Long ttl;

    @Builder
    public SMS(String phone, String code, Long ttl) {
        this.phone = phone;
        this.code = code;
        this.ttl = ttl;
    }
}
