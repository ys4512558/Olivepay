package kr.co.olivepay.card.dto.res;

import lombok.Builder;

@Builder
public record AccountBalanceCheckRes(
    Boolean isValid,
    String failReason
) {

}
