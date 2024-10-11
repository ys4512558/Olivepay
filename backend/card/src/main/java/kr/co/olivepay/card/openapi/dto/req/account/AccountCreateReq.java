package kr.co.olivepay.card.openapi.dto.req.account;

import kr.co.olivepay.card.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.card.openapi.dto.req.abstracts.FintechRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCreateReq extends FintechRequest {

    private String accountTypeUniqueNo;

    @Builder
    public AccountCreateReq(FintechHeaderReq Header, String accountTypeUniqueNo) {
        super(Header);
        this.accountTypeUniqueNo = accountTypeUniqueNo;
    }
}
