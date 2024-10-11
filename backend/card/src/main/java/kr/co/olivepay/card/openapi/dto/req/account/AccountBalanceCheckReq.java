package kr.co.olivepay.card.openapi.dto.req.account;

import kr.co.olivepay.card.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.card.openapi.dto.req.abstracts.FintechRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountBalanceCheckReq extends FintechRequest {

    private String accountNo;

    @Builder
    public AccountBalanceCheckReq(FintechHeaderReq Header, String accountNo) {
        super(Header);
        this.accountNo = accountNo;
    }
}
