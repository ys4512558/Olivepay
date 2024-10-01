package kr.co.olivepay.funding.openapi.dto.res.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.olivepay.funding.openapi.dto.res.abstracts.FintechResponse;
import kr.co.olivepay.funding.openapi.dto.res.account.rec.AccountTransferRec;
import lombok.Data;

@Data
public class AccountTransferRes extends FintechResponse {

    @JsonProperty("REC")
    private List<AccountTransferRec> REC;
}
