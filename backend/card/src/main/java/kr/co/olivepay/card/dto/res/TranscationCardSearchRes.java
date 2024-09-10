package kr.co.olivepay.card.dto.res;

import lombok.Builder;

/**
 * 결제를 위한 카드 정보 조회를 위한 DTO
 * @param cardId
 * @param cardNumber
 * @param cvc
 * @param isDefault
 */
@Builder
public record TranscationCardSearchRes(
        Long cardId,
        String cardNumber,
        String cvc,
        Boolean isDefault
) {

}
