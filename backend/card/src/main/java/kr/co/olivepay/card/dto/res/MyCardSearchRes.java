package kr.co.olivepay.card.dto.res;

import lombok.Builder;

/**
 * 사용자가 자신의 카드 목록 조회 시 요청하는 DTO
 * @param cardId
 * @param realCardNumber
 * @param isDefault
 * @param cardCompany
 */
@Builder
public record MyCardSearchRes(
        Long cardId,
        String realCardNumber,
        Boolean isDefault,
        String cardCompany
) {

}
