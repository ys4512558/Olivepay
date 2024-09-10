package kr.co.olivepay.card.dto.res;

import lombok.Builder;

/**
 * 결제 시 카드 정보 반환을 위한 DTO
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
