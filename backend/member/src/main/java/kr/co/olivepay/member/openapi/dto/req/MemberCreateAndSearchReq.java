package kr.co.olivepay.member.openapi.dto.req;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
public class MemberCreateAndSearchReq{

    private String apiKey;
    private String userId;

    @Builder
    public MemberCreateAndSearchReq(String userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
    }
}
