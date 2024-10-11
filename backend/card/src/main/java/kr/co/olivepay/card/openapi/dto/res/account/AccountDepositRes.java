package kr.co.olivepay.card.openapi.dto.res.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.olivepay.card.openapi.dto.res.abstracts.FintechResponse;
import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountDepositRec;
import lombok.Data;

@Data
public class AccountDepositRes extends FintechResponse {

    @JsonProperty("REC")
    private AccountDepositRec REC;
}
