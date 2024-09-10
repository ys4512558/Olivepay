package kr.co.olivepay.card.dto.req;

import lombok.Builder;

@Builder
public record CardSearchReq(
        Long cardId,
        Boolean isPublic
) {

}
