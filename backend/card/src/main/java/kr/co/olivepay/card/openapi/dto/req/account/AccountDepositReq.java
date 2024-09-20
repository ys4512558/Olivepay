package kr.co.olivepay.card.openapi.dto.req.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.olivepay.card.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.card.openapi.dto.req.abstracts.FintechRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDepositReq extends FintechRequest {

    @JsonProperty("accountNo")
    private String accountNo;

    @JsonProperty("transactionBalance")
    private String transactionBalance;

    @JsonProperty("transactionSummary")
    private String transactionSummary;

    @Builder
    public AccountDepositReq(FintechHeaderReq Header, String accountNo, String transactionBalance, String transactionSummary) {
        super(Header);
        this.accountNo = accountNo;
        this.transactionBalance = transactionBalance;
        this.transactionSummary = transactionSummary;
    }
}
