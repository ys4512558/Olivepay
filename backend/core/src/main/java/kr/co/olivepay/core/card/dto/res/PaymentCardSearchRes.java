package kr.co.olivepay.core.card.dto.res;

import kr.co.olivepay.core.card.dto.res.enums.CardType;
import lombok.Builder;

/**
 * 결제 시 카드 정보 반환을 위한 DTO
 *
 * @param cardId
 * @param cardNumber
 * @param cvc
 * @param isDefault
 */
@Builder
public record PaymentCardSearchRes(
        Long cardId,
        String cardNumber,
        String cvc,
        Boolean isDefault,
        CardType cardType
) {

}
