package kr.co.olivepay.card.openapi.dto.req.card;

import kr.co.olivepay.card.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.card.openapi.dto.req.abstracts.FintechRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardCreateReq extends FintechRequest {

    // 카드 상품 번호
    private String cardUniqueNo;

    // 입출금 계좌번호
    private String withdrawalAccountNo;

    // 출금 요일 (1: 월요일)
    private final Integer withdrawalDate = 1;

    @Builder
    public CardCreateReq(FintechHeaderReq Header, String cardUniqueNo, String withdrawalAccountNo) {
        super(Header);
        this.cardUniqueNo = cardUniqueNo;
        this.withdrawalAccountNo = withdrawalAccountNo;
    }
}
