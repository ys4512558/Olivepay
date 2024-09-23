package kr.co.olivepay.card.client.dto.req;

import lombok.Builder;

@Builder
public record UserKeyRes(
        String userKey
) {

}
