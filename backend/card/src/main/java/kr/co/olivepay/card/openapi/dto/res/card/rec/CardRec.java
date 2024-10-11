package kr.co.olivepay.card.openapi.dto.res.card.rec;

import lombok.*;

/**
 * 카드 생성 시 REC 필드를 통해 반환되는 객체 값
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardRec {

    private String cardNo;
    private String cvc;
    private String cardUniqueNo;
    private String cardIssuerCode;
    private String cardIssuerName;
    private String cardName;
    private String baselinePerformance;
    private String maxBenefitLimit;
    private String cardDescription;
    private String cardExpiryDate;
    private String withdrawalAccountNo;
    private String withdrawalDate;

}
